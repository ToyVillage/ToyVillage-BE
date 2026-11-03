package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface AnimalLegalDesignationRepository extends JpaRepository<AnimalLegalDesignation, Long> {
    @EntityGraph(attributePaths = "animalLegalStatus")
    List<AnimalLegalDesignation> findAllByAnimalKind_IdIn(List<Long> animalKindIds);

    List<AnimalLegalDesignation> findAllByAnimalKind(AnimalKind animalKind);

    void deleteAllByAnimalKindId(Long animalKindId);

    void deleteAllByAnimalLegalStatusId(Long animalLegalStatusId);
}
