package com.personal;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.personal.service.AccoutService;
import com.personal.service.ArticleService;
import com.personal.service.ContactsService;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

	@Inject
	protected UserService userService;
	@Inject
	protected AccoutService accoutService;
	@Inject
	protected PhotoService photoService;
	@Inject
	protected ArticleService articleService;
	@Inject
	protected ContactsService contactsService;

}
