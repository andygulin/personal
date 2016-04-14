package com.personal.data.ipt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import com.personal.data.DataHelper;
import com.personal.entity.Article;
import com.personal.entity.ArticleType;
import com.personal.entity.User;
import com.personal.service.ArticleService;
import com.personal.service.UserService;

public class IptTextThread implements Runnable {

	private UserService userService;
	private ArticleService articleService;
	private CountDownLatch latch;
	private BlockingQueue<Article> queue;

	public IptTextThread(UserService userService,
			ArticleService articleService, CountDownLatch latch,
			BlockingQueue<Article> queue) {
		this.userService = userService;
		this.articleService = articleService;
		this.latch = latch;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		Article article = null;
		while (true) {
			try {
				article = queue.poll(DataHelper.WAIT_TIMEOUT,
						DataHelper.WAIT_TIMEUNIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (article != null) {
				User user = userService.getByUsername(DataHelper.USERNAME);
				final String qiushiId = articleService
						.getArticleTypeIdByName("糗事");
				ArticleType articleType = articleService
						.getArticleType(qiushiId);
				article.setCreateUser(user);
				article.setType(articleType);
				articleService.saveArticle(article);
				System.out.println("save article title -> "
						+ article.getTitle());
			} else {
				return;
			}
		}
	}
}
