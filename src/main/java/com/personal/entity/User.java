package com.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User extends IdEntity {
	private static final long serialVersionUID = -6062423513774810596L;

	@Column(length = 20)
	private String username;

	@Column(length = 20)
	private String nickname;

	@Column(length = 255)
	private String password;

	private byte isEnabled;

	private byte level;

	@Column(updatable = false)
	private Date createDate;

	private Date lastLoginDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(byte isEnabled) {
		this.isEnabled = isEnabled;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

}
