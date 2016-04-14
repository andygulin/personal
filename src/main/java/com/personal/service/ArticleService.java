package com.personal.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.personal.entity.Article;
import com.personal.entity.ArticleType;
import com.personal.repository.ArticleDao;
import com.personal.repository.ArticleTypeDao;
import com.personal.tools.DynamicSpecifications;
import com.personal.tools.SearchFilter;

@Service
public class ArticleService {

	@Inject
	private ArticleDao articleDao;

	public List<Article> getArticleList() {
		return articleDao.findAll();
	}

	public Article getArticle(String id) {
		return articleDao.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Article saveArticle(Article article) {
		return articleDao.save(article);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Article> batchSaveArticle(List<Article> articles) {
		return articleDao.save(articles);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteArticle(String id) {
		articleDao.delete(id);
	}

	public Page<Article> findAricleByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildArticlePageRequest(pageNumber, pageSize, sortType);
		Specification<Article> spec = buildArticleSpecification(searchParams);
		return articleDao.findAll(spec, pageRequest);
	}

	private PageRequest buildArticlePageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	private Specification<Article> buildArticleSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Article> spec = DynamicSpecifications.bySearchFilter(filters.values(), Article.class);
		return spec;
	}

	public Page<Article> getArticleListOrderByCreateDate(int page, int pageSize) {
		return articleDao.findAll(new PageRequest(page, pageSize, new Sort(Direction.DESC, "createDate")));
	}

	// ArticleType
	@Autowired
	private ArticleTypeDao articleTypeDao;

	public List<ArticleType> getArticleTypeList() {
		return articleTypeDao.findAll();
	}

	public ArticleType getArticleType(String id) {
		return articleTypeDao.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ArticleType saveArticleType(ArticleType articleType) {
		return articleTypeDao.save(articleType);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ArticleType> batchSaveArticleType(List<ArticleType> articleTypes) {
		return articleTypeDao.save(articleTypes);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteArticleType(String id) {
		articleTypeDao.delete(id);
	}

	public List<ArticleType> getArticleTypeCount() {
		List<Object[]> list = articleTypeDao.getArticleTypeCount();
		List<ArticleType> articleTypes = Lists.newArrayList();
		for (Object[] objs : list) {
			Object[] obj = objs;
			ArticleType articleType = new ArticleType();
			articleType.setId(obj[0].toString());
			articleType.setName(obj[1].toString());
			articleType.setCount(NumberUtils.toLong(obj[2].toString()));
			articleType.setIsDefault(BooleanUtils.toBoolean(obj[3].toString()));
			articleTypes.add(articleType);
		}
		return articleTypes;
	}

	public String getArticleTypeIdByName(String name) {
		return articleTypeDao.getArticleTypeIdByName(name);
	}
}
