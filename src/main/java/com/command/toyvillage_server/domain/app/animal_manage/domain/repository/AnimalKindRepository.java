package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
    Optional<AnimalKind> findByIdAndDeleteYnFalse(Long animalKindId);

    boolean existsByIdAndDeleteYnFalse(Long animalKindId);

    Page<AnimalKind> findAllByAnimalTaxonomic(AnimalTaxonomic animalTaxonomic, Pageable pageable);

    @Query(
        value = """
            SELECT animalKind
            FROM AnimalKind animalKind
            WHERE animalKind.deleteYn = false
              AND (:animalTaxonomic IS NULL OR animalKind.animalTaxonomic = :animalTaxonomic)
              AND (
                :keyword IS NULL
                OR :keyword = ''
                OR LOWER(animalKind.kindName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR EXISTS (
                    SELECT animalManage.id
                    FROM AnimalManage animalManage
                    WHERE animalManage.animalKind = animalKind
                      AND animalManage.deleteYn = false
                      AND LOWER(animalManage.animalName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
              )
            """,
        countQuery = """
            SELECT COUNT(animalKind)
            FROM AnimalKind animalKind
            WHERE animalKind.deleteYn = false
              AND (:animalTaxonomic IS NULL OR animalKind.animalTaxonomic = :animalTaxonomic)
              AND (
                :keyword IS NULL
                OR :keyword = ''
                OR LOWER(animalKind.kindName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR EXISTS (
                    SELECT animalManage.id
                    FROM AnimalManage animalManage
                    WHERE animalManage.animalKind = animalKind
                      AND animalManage.deleteYn = false
                      AND LOWER(animalManage.animalName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
              )
            """
    )
    Page<AnimalKind> findAllByFilter(
        @Param("animalTaxonomic") AnimalTaxonomic animalTaxonomic,
        @Param("keyword") String keyword,
        Pageable pageable
    );
}
