package com.personal.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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

import com.google.common.collect.Lists;
import com.personal.entity.Contacts;
import com.personal.entity.ContactsType;
import com.personal.repository.ContactsRepository;
import com.personal.repository.ContactsTypeRepository;
import com.personal.tools.DynamicSpecifications;
import com.personal.tools.SearchFilter;

@Service
public class ContactsService {

	@Inject
	private ContactsRepository contactsRepository;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Contacts> getContactsList() {
		return contactsRepository.findAll();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Contacts getContacts(String id) {
		return contactsRepository.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Contacts saveContacts(Contacts contacts) {
		return contactsRepository.save(contacts);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteContacts(String id) {
		contactsRepository.delete(id);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Page<Contacts> findContactsByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildContactsPageRequest(pageNumber, pageSize, sortType);
		Specification<Contacts> spec = buildContactsSpecification(searchParams);
		return contactsRepository.findAll(spec, pageRequest);
	}

	private PageRequest buildContactsPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createDate");
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	private Specification<Contacts> buildContactsSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Contacts> spec = DynamicSpecifications.bySearchFilter(filters.values(), Contacts.class);
		return spec;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Page<Contacts> getArticleListOrderByCreateDate(int page, int pageSize) {
		return contactsRepository.findAll(new PageRequest(page, pageSize, new Sort(Direction.DESC, "createDate")));
	}

	// ArticleType
	@Inject
	private ContactsTypeRepository contactsTypeRepository;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ContactsType> getContactsTypeList() {
		return contactsTypeRepository.findAll();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ContactsType getContactsType(String id) {
		return contactsTypeRepository.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ContactsType saveContactsType(ContactsType contactsType) {
		return contactsTypeRepository.save(contactsType);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ContactsType> batchSaveContactsType(List<ContactsType> contactsTypes) {
		return contactsTypeRepository.save(contactsTypes);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteContactsType(String id) {
		contactsTypeRepository.delete(id);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ContactsType> getContactsTypeCount() {
		List<Object[]> list = contactsTypeRepository.getContactsTypeCount();
		List<ContactsType> contactsTypes = Lists.newArrayList();
		for (Object[] objs : list) {
			Object[] obj = objs;
			ContactsType contactsType = new ContactsType();
			contactsType.setId(obj[0].toString());
			contactsType.setName(obj[1].toString());
			contactsType.setCount(NumberUtils.toLong(obj[2].toString()));
			contactsType.setIsDefault(BooleanUtils.toBoolean(obj[3].toString()));
			contactsTypes.add(contactsType);
		}
		return contactsTypes;
	}
}
