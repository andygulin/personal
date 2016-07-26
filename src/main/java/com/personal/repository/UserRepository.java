package com.personal.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personal.entity.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	@Query("from User u where u.username=:username and u.password=:password")
	User check(@Param("username") String username, @Param("password") String password);

	@Query("from User u where u.username=:username")
	User getByUsername(@Param("username") String username);

	@Modifying
	@Query("update User u set u.lastLoginDate=:lastLoginDate where u.id=:id")
	void updateLastLoginDate(@Param("lastLoginDate") Date lastLoginDate, @Param("id") String id);
}
