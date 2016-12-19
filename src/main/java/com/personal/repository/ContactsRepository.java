package com.personal.repository;

import com.personal.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactsRepository extends JpaRepository<Contacts, String>, JpaSpecificationExecutor<Contacts> {

}
