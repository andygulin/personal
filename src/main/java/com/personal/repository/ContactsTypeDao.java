package com.personal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.personal.entity.ContactsType;

public interface ContactsTypeDao extends JpaRepository<ContactsType, String>, JpaSpecificationExecutor<ContactsType> {

	@Query("select a.id,a.name,(select count(b.type.id) from Contacts b where b.type.id=a.id) as count,a.isDefault from ContactsType a")
	List<Object[]> getContactsTypeCount();
}
