package org.workswap.datasource.central.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.workswap.datasource.central.model.user.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByName(String name);

    List<Permission> findAllByIdIn(List<Long> ids);
}
