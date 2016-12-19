package com.personal.repository;

import com.personal.entity.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypeRepository extends JpaRepository<ArticleType, String>, JpaSpecificationExecutor<ArticleType> {

    @Query("select a.id,a.name,(select count(b.type.id) from Article b where b.type.id=a.id) as count,a.isDefault from ArticleType a")
    List<Object[]> getArticleTypeCount();

    @Query("select a.id from ArticleType a where a.name=:name")
    String getArticleTypeIdByName(@Param("name") String name);
}
