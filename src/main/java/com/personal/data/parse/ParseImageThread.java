package com.personal.data.parse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.personal.data.DataHelper;
import com.personal.data.WebVO;
import com.personal.entity.Photo;
import com.personal.entity.PhotoType;

public class ParseImageThread implements Runnable {

	private List<WebVO> webs;
	private BlockingQueue<Photo> queue;
	private CountDownLatch latch;

	public ParseImageThread(CountDownLatch latch, BlockingQueue<Photo> queue, List<WebVO> webs) {
		this.latch = latch;
		this.queue = queue;
		this.webs = webs;
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		PhotoType photoType = null;
		String url = null;
		String carId = "";
		Document doc = null;
		for (WebVO webVO : webs) {
			photoType = webVO.getPhotoType();
			carId = webVO.getCatId();
			for (int i = 1; i <= DataHelper.DEFAULT_PAGE_SIZE; i++) {
				String page = "p" + i + "/";
				url = carId + page;
				try {
					doc = Jsoup.connect(url).userAgent(DataHelper.USER_AGENT).timeout(DataHelper.TIMEOUT).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Elements els = doc.select("div#pa1 ul#imgList li");
				for (Element el : els) {
					String t = el.select("a img").attr("src");
					String src = t.replace("t_", "");
					String fileName = src.substring(src.lastIndexOf("/") + 1);
					String alt = el.select("a img").attr("alt");
					Photo photo = new Photo();
					photo.setCreateDate(new Date());
					photo.setCreateUser(null);
					photo.setId(null);
					photo.setRemark(alt);
					photo.setSrcName(fileName);
					photo.setType(photoType);
					File file = new File(FileUtils.getTempDirectoryPath(), FilenameUtils.getName(src));
					try {
						FileUtils.copyURLToFile(new URL(src), file);
						byte[] image = FileUtils.readFileToByteArray(file);
						photo.setImage(image);
						FileUtils.deleteQuietly(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					queue.offer(photo);
					System.out.println("parse image src -> " + photo.getSrcName());
				}
			}
		}
	}
}
