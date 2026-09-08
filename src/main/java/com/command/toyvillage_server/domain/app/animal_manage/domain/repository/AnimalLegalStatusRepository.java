package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalLegalStatusRepository extends JpaRepository<AnimalLegalStatus, Long> {
}
