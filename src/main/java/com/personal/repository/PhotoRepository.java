package com.personal.repository;

import com.personal.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, String>, JpaSpecificationExecutor<Photo> {

    @Query("from Photo a where a.type.id=:id")
    List<Photo> getListById(@Param("id") String id);

    @Query("from Photo a where a.type.id=:id group by a.srcName")
    List<Photo> getDistinctListById(@Param("id") String id);

    @Modifying
    @Query("delete from Photo a where a.type.id=:id")
    void deleteAllById(@Param("id") String id);
}
