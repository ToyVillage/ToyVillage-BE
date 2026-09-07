package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalLegalDesignationRepository extends JpaRepository<AnimalLegalDesignation, Long> {
    List<AnimalLegalDesignation> findAllByAnimalKind(AnimalKind animalKind);

    void deleteAllByAnimalKind(AnimalKind animalKind);
}
