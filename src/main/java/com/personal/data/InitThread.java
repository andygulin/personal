package com.personal.data;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.personal.entity.ArticleType;
import com.personal.entity.ContactsType;
import com.personal.entity.PhotoType;
import com.personal.entity.User;
import com.personal.service.ArticleService;
import com.personal.service.ContactsService;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

public class InitThread implements Runnable {

	private UserService userService;
	private ArticleService articleService;
	private PhotoService photoService;
	private ContactsService contactsService;
	private CountDownLatch latch;

	public InitThread(CountDownLatch latch, UserService userService, ArticleService articleService,
			PhotoService photoService, ContactsService contactsService) {
		this.userService = userService;
		this.articleService = articleService;
		this.photoService = photoService;
		this.contactsService = contactsService;
		this.latch = latch;
	}

	@Override
	public void run() {

		final String[] contactsTypes = { "默认分类", "亲人", "朋友", "同事", "其他" };

		final String[] articleTypes = { "默认文章", "技术文章", "糗事", "备忘录", "其他" };

		final String[] photoTypes = { "默认相册", "大众", "法拉利", "捷豹", "兰博基尼", "路虎", "宝马", "奔驰", "奥迪", "保时捷", "阿斯顿·马丁", "宾利",
				"玛莎拉蒂", "日产", "雪佛兰" };

		User user = userService.getByUsername(DataHelper.USERNAME);
		if (user == null) {
			User u = new User();
			u.setUsername(DataHelper.USERNAME);
			u.setPassword(DataHelper.PASSWORD);
			u.setIsEnabled((byte) 1);
			u.setNickname("顾大林");
			u.setCreateDate(new Date());
			u.setLevel((byte) 1);
			userService.save(u);
		}

		List<ArticleType> articleType = articleService.getArticleTypeList();
		if (articleType.size() == 0) {
			for (String name : articleTypes) {
				ArticleType type = new ArticleType();
				type.setName(name);
				if (name.equals("默认文章")) {
					type.setIsDefault(true);
				} else {
					type.setIsDefault(false);
				}
				articleType.add(type);
			}
			articleService.batchSaveArticleType(articleType);
		}

		List<PhotoType> photo = photoService.getPhotoTypeList();
		if (photo.size() == 0) {
			for (String name : photoTypes) {
				PhotoType p = new PhotoType();
				p.setCover(null);
				p.setName(name);
				p.setCreateDate(new Date());
				if (name.equals("默认相册")) {
					p.setIsDefault(true);
				} else {
					p.setIsDefault(false);
				}
				photo.add(p);
			}
			photoService.batchSavePhotoType(photo);
		}

		List<ContactsType> contacts = contactsService.getContactsTypeList();
		if (contacts.size() == 0) {
			for (String name : contactsTypes) {
				ContactsType p = new ContactsType();
				p.setName(name);
				if (name.equals("默认分类")) {
					p.setIsDefault(true);
				} else {
					p.setIsDefault(false);
				}
				contacts.add(p);
			}
			contactsService.batchSaveContactsType(contacts);
		}
		latch.countDown();
	}
}
