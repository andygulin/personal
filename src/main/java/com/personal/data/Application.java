package com.personal.data;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.personal.data.ipt.IptTextThread;
import com.personal.data.parse.ParseTextThread;
import com.personal.entity.Article;
import com.personal.entity.PhotoType;
import com.personal.service.ArticleService;
import com.personal.service.ContactsService;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

public class Application {

	private static ApplicationContext context = null;
	private static UserService userService = null;
	private static PhotoService photoService = null;
	private static ArticleService articleService = null;
	private static ContactsService contactsService = null;

	private static List<WebVO> webs = Lists.newArrayList();

	static {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		userService = context.getBean(UserService.class);
		photoService = context.getBean(PhotoService.class);
		articleService = context.getBean(ArticleService.class);
		contactsService = context.getBean(ContactsService.class);

		final PhotoType ID1 = photoService.getPhotoTypeByName("宝马");
		final PhotoType ID2 = photoService.getPhotoTypeByName("奥迪");
		final PhotoType ID3 = photoService.getPhotoTypeByName("保时捷");
		final PhotoType ID4 = photoService.getPhotoTypeByName("法拉利");
		final PhotoType ID5 = photoService.getPhotoTypeByName("兰博基尼");
		final PhotoType ID6 = photoService.getPhotoTypeByName("奔驰");
		final PhotoType ID7 = photoService.getPhotoTypeByName("路虎");
		final PhotoType ID8 = photoService.getPhotoTypeByName("捷豹");
		final PhotoType ID9 = photoService.getPhotoTypeByName("大众");
		final PhotoType ID10 = photoService.getPhotoTypeByName("阿斯顿·马丁");
		final PhotoType ID11 = photoService.getPhotoTypeByName("宾利");
		final PhotoType ID12 = photoService.getPhotoTypeByName("玛莎拉蒂");
		final PhotoType ID13 = photoService.getPhotoTypeByName("日产");
		final PhotoType ID14 = photoService.getPhotoTypeByName("雪佛兰");

		webs.add(new WebVO(ID1, "2196", "M3"));
		webs.add(new WebVO(ID1, "2847", "5GT"));
		webs.add(new WebVO(ID1, "270", "6系"));
		webs.add(new WebVO(ID1, "159", "X5"));
		webs.add(new WebVO(ID1, "587", "X6"));
		webs.add(new WebVO(ID1, "161", "Z4"));
		webs.add(new WebVO(ID1, "2726", "M5"));

		webs.add(new WebVO(ID2, "692", "A4L"));
		webs.add(new WebVO(ID2, "538", "A5"));
		webs.add(new WebVO(ID2, "740", "A7"));
		webs.add(new WebVO(ID2, "2739", "S8"));
		webs.add(new WebVO(ID2, "2740", "TTS"));
		webs.add(new WebVO(ID2, "511", "R8"));

		webs.add(new WebVO(ID3, "162", "911"));
		webs.add(new WebVO(ID3, "703", "Panamera"));
		webs.add(new WebVO(ID3, "172", "卡宴"));

		webs.add(new WebVO(ID4, "889", "458"));
		webs.add(new WebVO(ID4, "2682", "F12"));
		webs.add(new WebVO(ID4, "2261", "FF"));

		webs.add(new WebVO(ID5, "2277", "Aventador"));
		webs.add(new WebVO(ID5, "354", "Gallardo"));

		webs.add(new WebVO(ID6, "2719", "CLS级AMG"));
		webs.add(new WebVO(ID6, "2717", "C级AMG"));
		webs.add(new WebVO(ID6, "914", "SLS级AMG"));
		webs.add(new WebVO(ID6, "2197", "S级AMG"));
		webs.add(new WebVO(ID6, "588", "C级"));

		webs.add(new WebVO(ID7, "69", "揽胜"));
		webs.add(new WebVO(ID7, "754", "揽胜极光"));
		webs.add(new WebVO(ID7, "850", "揽胜运动版"));

		webs.add(new WebVO(ID8, "2903", "F-TYPE"));
		webs.add(new WebVO(ID8, "589", "XF"));
		webs.add(new WebVO(ID8, "178", "XJ"));
		webs.add(new WebVO(ID8, "456", "XL"));

		webs.add(new WebVO(ID9, "700", "CC"));
		webs.add(new WebVO(ID9, "372", "高尔夫"));
		webs.add(new WebVO(ID9, "224", "辉腾"));
		webs.add(new WebVO(ID9, "210", "甲壳虫"));
		webs.add(new WebVO(ID9, "669", "尚酷"));
		webs.add(new WebVO(ID9, "82", "途锐"));

		webs.add(new WebVO(ID10, "266", "DB9"));
		webs.add(new WebVO(ID10, "729", "One-77"));

		webs.add(new WebVO(ID11, "305", "欧陆"));

		webs.add(new WebVO(ID12, "289", "总裁"));

		webs.add(new WebVO(ID13, "436", "GT-R"));

		webs.add(new WebVO(ID14, "678", "科迈罗Camaro"));
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

		// 图片
		// BlockingQueue<Photo> photoQueue = new ArrayBlockingQueue<Photo>(20);
		// List<List<WebVO>> splitList = Lists.partition(webs, 5);
		// for (List<WebVO> webs : splitList) {
		// service.execute(new ParseImageThread(latch, photoQueue, webs));
		// service.execute(new IptImageThread(latch, photoQueue, photoService,
		// userService));
		// }

		service.shutdown();
	}
}
