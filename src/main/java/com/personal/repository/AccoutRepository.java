package com.personal.repository;

import com.personal.entity.Accout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccoutRepository extends JpaRepository<Accout, String>, JpaSpecificationExecutor<Accout> {
}
