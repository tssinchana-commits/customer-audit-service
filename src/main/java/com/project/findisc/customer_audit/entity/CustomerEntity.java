package com.project.findisc.customer_audit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers_rel")
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
}