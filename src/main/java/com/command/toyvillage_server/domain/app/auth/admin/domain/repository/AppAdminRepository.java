package com.command.toyvillage_server.domain.app.auth.admin.domain.repository;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppAdminRepository extends JpaRepository<AppAdmin, Long> {
    Optional<AppAdmin> findByUsernameAndDeleteStatusFalse(String username);

    Optional<AppAdmin> findByIdAndDeleteStatusFalse(Long id);

    Optional<AppAdmin> findByIdAndRoleAndDeleteStatusFalse(Long id, AppAdminRole role);

    List<AppAdmin> findAllByIdInAndDeleteStatusFalse(List<Long> ids);

    boolean existsByIdAndDeleteStatusFalse(Long id);

    boolean existsByUsername(String username);

    List<AppAdmin> findByRoleAndDeleteStatusFalseOrderByNameAsc(AppAdminRole role);

    List<AppAdmin> findAllByRoleAndDeleteStatusFalseOrderByIdAsc(AppAdminRole role);
}
