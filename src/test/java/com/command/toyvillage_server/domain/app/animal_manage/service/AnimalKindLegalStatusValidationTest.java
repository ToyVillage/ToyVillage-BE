package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalLegalStatusNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalKindRequest;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalKindLegalStatusValidationTest {
    @Mock
    private AnimalKindRepository animalKindRepository;

    @Mock
    private AnimalLegalStatusRepository animalLegalStatusRepository;

    @Mock
    private AnimalLegalDesignationRepository animalLegalDesignationRepository;

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private CreateAnimalKindService createAnimalKindService;

    @InjectMocks
    private UpdateAnimalKindService updateAnimalKindService;

    @Test
    void 종_생성시_없는_법정분류가_있으면_저장하지_않는다() {
        AnimalKindRequest request = request();
        when(animalLegalStatusRepository.findAllById(request.animalLegalDesignation())).thenReturn(List.of());

        assertThatThrownBy(() -> createAnimalKindService.execute(request))
            .isSameAs(AnimalLegalStatusNotFoundException.EXCEPTION);

        verify(animalKindRepository, never()).save(any());
        verify(fileRepository, never()).findByFileKey(anyString());
    }

    @Test
    void 종_수정시_없는_법정분류가_있으면_기존_연결을_삭제하지_않는다() {
        AnimalKindRequest request = request();
        AnimalKind animalKind = AnimalKind.builder()
            .kindName("기존 종")
            .engName("Existing")
            .scientificName("Existing species")
            .animalTaxonomic(AnimalTaxonomic.MAMMALS)
            .detailKind("기존 세부분류")
            .build();
        when(animalKindRepository.findById(1L)).thenReturn(Optional.of(animalKind));
        when(animalLegalStatusRepository.findAllById(request.animalLegalDesignation())).thenReturn(List.of());

        assertThatThrownBy(() -> updateAnimalKindService.execute(1L, request))
            .isSameAs(AnimalLegalStatusNotFoundException.EXCEPTION);

        verify(animalLegalDesignationRepository, never()).deleteAllByAnimalKind(animalKind);
        verify(fileRepository, never()).findByFileKey(anyString());
    }

    private AnimalKindRequest request() {
        return new AnimalKindRequest(
            "카피바라",
            "Capybara",
            "Hydrochoerus hydrochaeris",
            AnimalTaxonomic.MAMMALS,
            "설치목",
            "file-key",
            List.of(999999999L)
        );
    }
}
