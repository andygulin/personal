package com.personal.service;

import com.google.common.collect.Lists;
import com.personal.data.InitThread;
import com.personal.data.WebVO;
import com.personal.data.ipt.IptImageThread;
import com.personal.data.parse.ParseImageThread;
import com.personal.entity.Photo;
import com.personal.entity.PhotoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.*;

@Lazy(false)
@Service
public class ParseService {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContactsService contactsService;

    @PostConstruct
    public void parse() {
        List<WebVO> webList = Lists.newArrayList();

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

        webList.add(new WebVO(ID1, "2196", "M3"));
        webList.add(new WebVO(ID1, "2847", "5GT"));
        webList.add(new WebVO(ID1, "270", "6系"));
        webList.add(new WebVO(ID1, "159", "X5"));
        webList.add(new WebVO(ID1, "587", "X6"));
        webList.add(new WebVO(ID1, "161", "Z4"));
        webList.add(new WebVO(ID1, "2726", "M5"));

        webList.add(new WebVO(ID2, "692", "A4L"));
        webList.add(new WebVO(ID2, "538", "A5"));
        webList.add(new WebVO(ID2, "740", "A7"));
        webList.add(new WebVO(ID2, "2739", "S8"));
        webList.add(new WebVO(ID2, "2740", "TTS"));
        webList.add(new WebVO(ID2, "511", "R8"));

        webList.add(new WebVO(ID3, "162", "911"));
        webList.add(new WebVO(ID3, "703", "Panamera"));
        webList.add(new WebVO(ID3, "172", "卡宴"));

        webList.add(new WebVO(ID4, "889", "458"));
        webList.add(new WebVO(ID4, "2682", "F12"));
        webList.add(new WebVO(ID4, "2261", "FF"));

        webList.add(new WebVO(ID5, "2277", "Aventador"));
        webList.add(new WebVO(ID5, "354", "Gallardo"));

        webList.add(new WebVO(ID6, "2719", "CLS级AMG"));
        webList.add(new WebVO(ID6, "2717", "C级AMG"));
        webList.add(new WebVO(ID6, "914", "SLS级AMG"));
        webList.add(new WebVO(ID6, "2197", "S级AMG"));
        webList.add(new WebVO(ID6, "588", "C级"));

        webList.add(new WebVO(ID7, "69", "揽胜"));
        webList.add(new WebVO(ID7, "754", "揽胜极光"));
        webList.add(new WebVO(ID7, "850", "揽胜运动版"));

        webList.add(new WebVO(ID8, "2903", "F-TYPE"));
        webList.add(new WebVO(ID8, "589", "XF"));
        webList.add(new WebVO(ID8, "178", "XJ"));
        webList.add(new WebVO(ID8, "456", "XL"));

        webList.add(new WebVO(ID9, "700", "CC"));
        webList.add(new WebVO(ID9, "372", "高尔夫"));
        webList.add(new WebVO(ID9, "224", "辉腾"));
        webList.add(new WebVO(ID9, "210", "甲壳虫"));
        webList.add(new WebVO(ID9, "669", "尚酷"));
        webList.add(new WebVO(ID9, "82", "途锐"));

        webList.add(new WebVO(ID10, "266", "DB9"));
        webList.add(new WebVO(ID10, "729", "One-77"));

        webList.add(new WebVO(ID11, "305", "欧陆"));

        webList.add(new WebVO(ID12, "289", "总裁"));

        webList.add(new WebVO(ID13, "436", "GT-R"));

        webList.add(new WebVO(ID14, "678", "科迈罗Camaro"));

        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(1);
        service.execute(new InitThread(latch, userService, articleService, photoService, contactsService));

        BlockingQueue<Photo> photoQueue = new ArrayBlockingQueue<>(20);
        List<List<WebVO>> splitList = Lists.partition(webList, 5);
        for (List<WebVO> webs : splitList) {
            service.execute(new ParseImageThread(latch, photoQueue, webs));
            service.execute(new IptImageThread(latch, photoQueue, photoService, userService));
        }
    }
}
