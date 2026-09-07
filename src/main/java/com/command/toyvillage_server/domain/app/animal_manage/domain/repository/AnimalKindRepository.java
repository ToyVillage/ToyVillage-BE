package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
}
