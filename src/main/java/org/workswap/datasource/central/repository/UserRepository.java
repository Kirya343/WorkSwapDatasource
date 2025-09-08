package org.workswap.datasource.central.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.workswap.common.enums.UserStatus;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.user.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    // Для OAuth2 пользователей
    Optional<User> findBySub(String sub);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
    long countByStatus(@Param("status") UserStatus status);

    Page<User> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<User> findByRolesContaining(Role role);
    List<User> findByRoles_Name(String roleName);
    List<User> findByRoles_NameIn(List<String> roleNames);
}
