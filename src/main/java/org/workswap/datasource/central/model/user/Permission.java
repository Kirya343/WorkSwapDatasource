package org.workswap.datasource.central.model.user;

import jakarta.persistence.*;

@Entity
public class Permission {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; 
}