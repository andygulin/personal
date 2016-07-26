package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.personal.entity.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, String>, JpaSpecificationExecutor<Contacts> {

}