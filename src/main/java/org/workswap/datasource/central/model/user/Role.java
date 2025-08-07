package org.workswap.datasource.central.model.user;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Role {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name; // Пример: "ADMIN", "MODERATOR", "USER"

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
