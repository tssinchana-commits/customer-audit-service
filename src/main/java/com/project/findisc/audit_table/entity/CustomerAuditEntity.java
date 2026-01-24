package com.project.findisc.audit_table.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityType;
    private Long entityId;
    private String action;
    private String reason;

    @Column(columnDefinition = "jsonb")
    private String data;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // 🔥 REQUIRED
    }
}