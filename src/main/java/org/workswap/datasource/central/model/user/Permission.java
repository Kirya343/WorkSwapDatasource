package org.workswap.datasource.central.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Permission {

    public Permission(String name) {
        this.name = name;
    }

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; 
}