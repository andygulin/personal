package com.personal.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.entity.User;
import com.personal.repository.UserRepository;

@Service
public class UserService {

	@Inject
	private UserRepository userRepository;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public User check(User user) {
		return userRepository.check(user.getUsername(), user.getPassword());
	}

	@Transactional(rollbackFor = Exception.class)
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<User> getList() {
		return userRepository.findAll();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public User getByUsername(String username) {
		return userRepository.getByUsername(username);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateLastLoginDate(User user) {
		userRepository.updateLastLoginDate(new Date(), user.getId());
	}
}
