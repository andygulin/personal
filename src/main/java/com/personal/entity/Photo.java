package com.personal.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Photo extends IdEntity {
    private static final long serialVersionUID = -1110370875601964461L;

    @Column(length = 255)
    private String srcName;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;

    @Column(length = 255)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    private PhotoType type;

    @Column(updatable = false)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createUser;

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PhotoType getType() {
        return type;
    }

    public void setType(PhotoType type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

}
