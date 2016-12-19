package com.personal.data.ipt;

import com.personal.data.DataHelper;
import com.personal.entity.Photo;
import com.personal.entity.PhotoType;
import com.personal.entity.User;
import com.personal.service.PhotoService;
import com.personal.service.UserService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class IptImageThread implements Runnable {

    private CountDownLatch latch;
    private BlockingQueue<Photo> queue;
    private PhotoService photoService;
    private UserService userService;

    public IptImageThread(CountDownLatch latch, BlockingQueue<Photo> queue, PhotoService photoService,
                          UserService userService) {
        this.latch = latch;
        this.queue = queue;
        this.photoService = photoService;
        this.userService = userService;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
        Photo photo = null;
        while (true) {
            try {
                photo = queue.poll(DataHelper.WAIT_TIMEOUT, DataHelper.WAIT_TIMEUNIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (photo != null) {
                User user = userService.getByUsername(DataHelper.USERNAME);
                photo.setCreateUser(user);
                this.photoService.savePhoto(photo);
                PhotoType photoType = photo.getType();
                if (photoType.getCover() == null) {
                    photoType.setCover(photo);
                    photoService.savePhotoType(photoType);
                }
                System.out.println("Save Image : " + photo.getSrcName());
            }
        }
    }
}
