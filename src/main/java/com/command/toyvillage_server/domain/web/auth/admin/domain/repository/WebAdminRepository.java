package com.command.toyvillage_server.domain.web.auth.admin.domain.repository;

import com.command.toyvillage_server.domain.web.auth.admin.domain.WebAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebAdminRepository extends JpaRepository<WebAdmin, Long> {
    Optional<WebAdmin> findByEmail(String email);

    boolean existsByEmail(String email);
}
