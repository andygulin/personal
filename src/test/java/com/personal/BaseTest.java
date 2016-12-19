package com.personal;

import com.personal.service.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.inject.Inject;

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
