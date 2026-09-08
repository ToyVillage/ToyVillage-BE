package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalLegalStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAnimalLegalStatusListService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;

    @Transactional(readOnly = true)
    public List<AnimalLegalStatusResponse> execute() {
        return animalLegalStatusRepository.findAll().stream()
            .map(AnimalLegalStatusResponse::from)
            .toList();
    }
}
