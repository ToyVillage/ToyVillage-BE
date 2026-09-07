package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalLegalStatusRepository extends JpaRepository<AnimalLegalStatus, Long> {
    List<AnimalLegalStatus> findAllById(List<Long> animalLegalStatusIds);
}
