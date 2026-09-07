package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalLegalDesignationRepository extends JpaRepository<AnimalLegalDesignation, Long> {
}
