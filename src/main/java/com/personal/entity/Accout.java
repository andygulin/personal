package com.personal.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Accout extends IdEntity {

    private static final long serialVersionUID = 607067217661692160L;

    @Column(length = 255)
    private String username;

    @Column(length = 255)
    private String password;

    @Column(length = 255)
    private String remark;

    @Column(updatable = false)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
