package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalManageRepository extends JpaRepository<AnimalManage, Long> {
    Page<AnimalManage> findAllByAnimalKindId(Long animalKindId, Pageable pageable);

    long countByAnimalKindId(Long animalKindId);

    List<AnimalManage> findAllByAnimalKind_Id(Long animalKindId);

    @Query("SELECT a.animalKind.id, COUNT(a.id) FROM AnimalManage a " +
        "WHERE a.animalKind.id IN :animalKindIds GROUP BY a.animalKind.id")
    List<Object[]> countByAnimalKindIds(@Param("animalKindIds") List<Long> animalKindIds);
}
