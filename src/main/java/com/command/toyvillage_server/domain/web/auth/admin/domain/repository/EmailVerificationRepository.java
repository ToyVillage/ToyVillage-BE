package com.command.toyvillage_server.domain.web.auth.admin.domain.repository;

import com.command.toyvillage_server.domain.web.auth.admin.domain.EmailVerification;
import org.springframework.data.repository.CrudRepository;

public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}

