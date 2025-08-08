package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.user.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByName(String name);

}
