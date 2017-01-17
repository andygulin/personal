package com.personal.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Article extends IdEntity {

    private static final long serialVersionUID = 9075293636042773700L;

    @Column
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @Lob
    @Column(columnDefinition = "longblob")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @Column
    private String srcName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    private ArticleType type;

    @Column(updatable = false)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createUser;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
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
