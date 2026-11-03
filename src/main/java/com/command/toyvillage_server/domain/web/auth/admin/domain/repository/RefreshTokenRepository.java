package com.command.toyvillage_server.domain.web.auth.admin.domain.repository;

import com.command.toyvillage_server.domain.web.auth.admin.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
