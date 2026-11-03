package com.command.toyvillage_server.domain.app.all_activities.service.animal_observation;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.animal_observation.AnimalObservationAllResponse;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalObservationAllQueryService {
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;

    @Transactional(readOnly = true)
    public List<AnimalObservationAllResponse> execute(LocalDate date) {
        List<AnimalObservation> observations = animalObservationRepository
                .findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAscIdAsc(
                        date.atStartOfDay(), date.plusDays(1).atStartOfDay()
                );
        if (observations.isEmpty()) {
            return List.of();
        }

        List<Long> observationIds = observations.stream().map(AnimalObservation::getId).toList();
        Map<Long, Long> fileCounts = animalObservationFileRepository
                .findAllByAnimalObservationIdIn(observationIds).stream()
                .collect(Collectors.groupingBy(file -> file.getAnimalObservation().getId(), Collectors.counting()));

        return observations.stream()
                .map(observation -> AnimalObservationAllResponse.from(
                        observation, fileCounts.getOrDefault(observation.getId(), 0L)
                ))
                .toList();
    }
}
