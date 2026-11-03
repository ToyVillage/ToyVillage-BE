package com.command.toyvillage_server.domain.web.faq.domain.repository;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
