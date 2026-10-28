package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalManageRepository extends JpaRepository<AnimalManage, Long> {
    @Query("""
            select a from AnimalManage a
            join fetch a.animalKind kind
            left join fetch a.animalImage
            order by kind.animalTaxonomic, kind.kindName, a.animalName, a.id
            """)
    List<AnimalManage> findExportPage(Pageable pageable);

    Optional<AnimalManage> findByIdAndDeleteYnFalse(Long animalManageId);

    boolean existsByIdAndDeleteYnFalse(Long animalManageId);

    List<AnimalManage> findAllByAnimalKindIdAndDeleteYnFalse(Long animalKindId);

    Page<AnimalManage> findAllByAnimalKindIdAndDeleteYnFalse(Long animalKindId, Pageable pageable);

    Page<AnimalManage> findAllByAnimalKindIdAndAnimalNameContainingIgnoreCaseAndDeleteYnFalse(
        Long animalKindId,
        String animalName,
        Pageable pageable
    );

    long countByAnimalKindIdAndDeleteYnFalse(Long animalKindId);

    @Query("SELECT a.animalKind.id, COUNT(a.id) FROM AnimalManage a " +
        "WHERE a.animalKind.id IN :animalKindIds AND a.deleteYn = false GROUP BY a.animalKind.id")
    List<Object[]> countByAnimalKindIds(@Param("animalKindIds") List<Long> animalKindIds);
}
