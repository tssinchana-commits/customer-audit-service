package com.project.findisc.audit_table.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityType;
    private Long entityId;
    private String action;
    private String reason;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String data;

    private LocalDateTime createdAt;
}