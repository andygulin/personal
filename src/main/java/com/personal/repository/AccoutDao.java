package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.personal.entity.Accout;

public interface AccoutDao extends JpaRepository<Accout, String>, JpaSpecificationExecutor<Accout> {
}
