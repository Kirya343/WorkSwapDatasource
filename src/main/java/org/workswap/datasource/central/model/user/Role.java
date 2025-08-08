package org.workswap.datasource.central.model.user;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Role {

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name; // Пример: "ADMIN", "MODERATOR", "USER"

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
