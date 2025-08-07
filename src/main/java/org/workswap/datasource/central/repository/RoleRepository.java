package org.workswap.datasource.central.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.user.Permission;
import org.workswap.datasource.central.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
    
    List<Role> findByPermissionsContaining(Permission permission);
}
