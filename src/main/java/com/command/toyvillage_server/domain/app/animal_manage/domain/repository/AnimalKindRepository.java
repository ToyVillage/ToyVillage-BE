package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
    Page<AnimalKind> findAllByAnimalTaxonomic(AnimalTaxonomic animalTaxonomic, Pageable pageable);
}
