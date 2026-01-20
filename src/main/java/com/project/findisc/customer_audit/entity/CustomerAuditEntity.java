package com.project.findisc.customer_audit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String action;
    private String reason;

    private LocalDateTime createdAt = LocalDateTime.now();
}