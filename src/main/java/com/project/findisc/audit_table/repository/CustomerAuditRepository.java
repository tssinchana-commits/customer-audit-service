package com.project.findisc.audit_table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.findisc.audit_table.entity.CustomerAuditEntity;

@Repository
public interface CustomerAuditRepository extends JpaRepository<CustomerAuditEntity, Long> {
}