package com.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personal.entity.Photo;

public interface PhotoDao extends JpaRepository<Photo, String>, JpaSpecificationExecutor<Photo> {

	@Query("from Photo a where a.type.id=:id")
	List<Photo> getListById(@Param("id") String id);

	@Query("from Photo a where a.type.id=:id group by a.srcName")
	List<Photo> getDistinctListById(@Param("id") String id);

	@Modifying
	@Query("delete from Photo a where a.type.id=:id")
	void deleteAllById(@Param("id") String id);
}
