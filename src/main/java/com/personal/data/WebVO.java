package com.personal.data;

import com.personal.entity.PhotoType;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WebVO {
    private final String CAR_PHOTO_URL = "http://car.autohome.com.cn/photolist/series/%s/";
    private PhotoType photoType;
    private String catId;
    private String name;

    public WebVO(PhotoType photoType, String catId, String name) {
        super();
        this.photoType = photoType;
        String cid = String.format(CAR_PHOTO_URL, catId);
        this.catId = cid;
        this.name = name;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
