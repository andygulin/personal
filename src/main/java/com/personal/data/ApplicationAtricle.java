package com.personal.data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.data.ipt.IptTextThread;
import com.personal.data.parse.ParseTextThread;
import com.personal.entity.Article;
import com.personal.service.ArticleService;
import com.personal.service.ContactsService;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

public class ApplicationAtricle {

	private static ApplicationContext context = null;
	private static UserService userService = null;
	private static PhotoService photoService = null;
	private static ArticleService articleService = null;
	private static ContactsService contactsService = null;

	static {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		userService = context.getBean(UserService.class);
		photoService = context.getBean(PhotoService.class);
		articleService = context.getBean(ArticleService.class);
		contactsService = context.getBean(ContactsService.class);
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(1);
		service.execute(new InitThread(latch, userService, articleService, photoService, contactsService));

		// 糗事最多抓取的页数
		final int MAX_PAGE = 1000;
		BlockingQueue<Article> articleQueue = new ArrayBlockingQueue<Article>(100);
		for (int begin = 1, end = begin + 99; end <= MAX_PAGE; begin += 100, end += 100) {
			service.execute(new ParseTextThread(latch, articleQueue, begin, end));
			service.execute(new IptTextThread(userService, articleService, latch, articleQueue));
		}

		service.shutdown();
	}
}
