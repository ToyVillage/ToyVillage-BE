package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalManageRepository extends JpaRepository<AnimalManage, Long> {
    Page<AnimalManage> findAllByAnimalKindId(Long animalKindId, Pageable pageable);

    long countByAnimalKindId(Long animalKindId);
}
