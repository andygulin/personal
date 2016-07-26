package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personal.entity.PhotoType;

public interface PhotoTypeRepository extends JpaRepository<PhotoType, String>, JpaSpecificationExecutor<PhotoType> {

	@Modifying
	@Query("update PhotoType a set a.cover.id=:id where a.id=:tid")
	void setCover(@Param("id") String id, @Param("tid") String tid);

	@Query("select (select count(b.type.id) from Photo b where b.type.id=a.id) as count from PhotoType a where a.id=:id")
	Long getPhotoTypeCount(@Param("id") String id);

	@Query("from PhotoType a where a.name=:name")
	PhotoType getPhotoTypeByName(@Param("name") String name);
}
