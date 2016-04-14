package com.personal.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personal.entity.User;
import com.personal.repository.UserDao;

@Service
public class UserService {

	@Inject
	private UserDao userDao;

	public User check(User user) {
		return userDao.check(user.getUsername(), user.getPassword());
	}

	@Transactional(rollbackFor = Exception.class)
	public User save(User user) {
		return userDao.save(user);
	}

	public List<User> getList() {
		return userDao.findAll();
	}

	public User getByUsername(String username) {
		return userDao.getByUsername(username);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateLastLoginDate(User user) {
		userDao.updateLastLoginDate(new Date(), user.getId());
	}
}
