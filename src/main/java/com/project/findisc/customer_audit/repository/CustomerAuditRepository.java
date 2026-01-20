package com.project.findisc.customer_audit.repository;

import com.project.findisc.customer_audit.entity.CustomerAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAuditRepository extends JpaRepository<CustomerAuditEntity, Long> {
}