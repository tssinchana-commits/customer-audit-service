package com.project.findisc.audit_table.entity;

import com.project.findisc.audit_table.listener.CustomerAuditListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers_rel")
@EntityListeners(CustomerAuditListener.class) // 🔥 REQUIRED
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    private LocalDateTime updatedAt; // 🔥 REQUIRED

    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}