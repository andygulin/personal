package com.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Contacts extends IdEntity {

	private static final long serialVersionUID = 3654824127486811440L;

	@Column(length = 20)
	private String name;

	private byte sex;

	@Column(length = 50)
	private String mobile;

	@Column(length = 50)
	private String tel;

	@Column(length = 255)
	private String address;

	@Column(length = 255)
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId")
	private ContactsType type;

	@Column(updatable = false)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createUserId", updatable = false)
	private User createUser;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ContactsType getType() {
		return type;
	}

	public void setType(ContactsType type) {
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
