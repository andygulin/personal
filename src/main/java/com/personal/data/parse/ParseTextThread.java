package com.personal.data.parse;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.personal.data.DataHelper;
import com.personal.entity.Article;

public class ParseTextThread implements Runnable {

	private int begin;
	private int end;
	private CountDownLatch latch;
	private BlockingQueue<Article> queue;

	public ParseTextThread(CountDownLatch latch, BlockingQueue<Article> queue,
			int begin, int end) {
		this.latch = latch;
		this.queue = queue;
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		Document doc = null;
		for (int i = begin; i <= end; i++) {
			try {
				doc = Jsoup.connect(DataHelper.PARSE_URL + i)
						.userAgent(DataHelper.USER_AGENT)
						.timeout(DataHelper.TIMEOUT).get();
				Elements els = doc.select("div .block");
				for (Element el : els) {
					String id = el.attr("id").split("_")[2];
					String text = el.select(".content").text();
					Elements thumbs = el.select(".thumb");
					Article article = new Article();
					article.setContent(text);
					article.setCreateDate(new Date());
					article.setCreateUser(null);
					article.setId(null);
					article.setTitle("糗事" + id);
					article.setType(null);
					if (thumbs.size() > 0) {
						String src = thumbs.select("img").attr("src");
						File file = new File(FileUtils.getTempDirectoryPath(),
								FilenameUtils.getName(src));
						FileUtils.copyURLToFile(new URL(src), file);
						byte[] image = FileUtils.readFileToByteArray(file);
						article.setImage(image);
						article.setSrcName(FilenameUtils.getName(src));
						FileUtils.deleteQuietly(file);
					}
					queue.offer(article);
					System.out.println("parse article title -> "
							+ article.getTitle());

				}
			} catch (SocketTimeoutException e) {
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
