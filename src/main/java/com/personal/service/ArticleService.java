package com.personal.service;

import com.google.common.collect.Lists;
import com.personal.entity.Article;
import com.personal.entity.ArticleType;
import com.personal.repository.ArticleRepository;
import com.personal.repository.ArticleTypeRepository;
import com.personal.tools.DynamicSpecifications;
import com.personal.tools.SearchFilter;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;
    // ArticleType
    @Inject
    private ArticleTypeRepository articleTypeRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Article getArticle(String id) {
        return articleRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Article> batchSaveArticle(List<Article> articles) {
        return articleRepository.save(articles);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(String id) {
        articleRepository.delete(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page<Article> findAricleByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                          String sortType) {
        PageRequest pageRequest = buildArticlePageRequest(pageNumber, pageSize, sortType);
        Specification<Article> spec = buildArticleSpecification(searchParams);
        return articleRepository.findAll(spec, pageRequest);
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page<Article> getArticleListOrderByCreateDate(int page, int pageSize) {
        return articleRepository.findAll(new PageRequest(page, pageSize, new Sort(Direction.DESC, "createDate")));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<ArticleType> getArticleTypeList() {
        return articleTypeRepository.findAll();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public ArticleType getArticleType(String id) {
        return articleTypeRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleType saveArticleType(ArticleType articleType) {
        return articleTypeRepository.save(articleType);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ArticleType> batchSaveArticleType(List<ArticleType> articleTypes) {
        return articleTypeRepository.save(articleTypes);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleType(String id) {
        articleTypeRepository.delete(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<ArticleType> getArticleTypeCount() {
        List<Object[]> list = articleTypeRepository.getArticleTypeCount();
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public String getArticleTypeIdByName(String name) {
        return articleTypeRepository.getArticleTypeIdByName(name);
    }
}
