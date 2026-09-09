package com.command.toyvillage_server.domain.app.auth.admin.domain.repository;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppAdminRepository extends JpaRepository<AppAdmin, Long> {
    Optional<AppAdmin> findByUsername(String username);

    boolean existsByUsername(String username);

    List<AppAdmin> findByRoleOrderByNameAsc(AppAdminRole role);

    List<AppAdmin> findAllByRoleOrderByIdAsc(AppAdminRole role);
}
