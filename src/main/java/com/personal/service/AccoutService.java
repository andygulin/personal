package com.personal.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personal.entity.Accout;
import com.personal.repository.AccoutDao;
import com.personal.tools.DynamicSpecifications;
import com.personal.tools.SearchFilter;

@Service
@Transactional
public class AccoutService {

	@Inject
	private AccoutDao accoutDao;

	public List<Accout> getList() {
		return accoutDao.findAll();
	}

	public Accout get(String id) {
		return accoutDao.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Accout save(Accout accout) {
		return accoutDao.save(accout);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		accoutDao.delete(id);
	}

	public Page<Accout> findByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Accout> spec = buildSpecification(searchParams);
		return accoutDao.findAll(spec, pageRequest);
	}

	private PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	private Specification<Accout> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Accout> spec = DynamicSpecifications.bySearchFilter(filters.values(), Accout.class);
		return spec;
	}

	public Page<Accout> getListOrderByCreateDate(int page, int pageSize) {
		return accoutDao.findAll(new PageRequest(page, pageSize, new Sort(Direction.DESC, "createDate")));
	}
}
