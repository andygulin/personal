package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.personal.entity.Article;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
}
