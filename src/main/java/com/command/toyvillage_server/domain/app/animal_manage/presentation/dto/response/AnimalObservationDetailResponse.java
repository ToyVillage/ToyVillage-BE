package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservationFile;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AnimalObservationDetailResponse(
    Long animalObservationId,
    String title,
    String content,
    LocalDate createdAt,
    String authorName,
    List<FileResponse> files
) {
    public static AnimalObservationDetailResponse from(
        AnimalObservation observation,
        List<AnimalObservationFile> observationFiles
    ) {
        return AnimalObservationDetailResponse.builder()
            .animalObservationId(observation.getId())
            .title(observation.getTitle())
            .content(observation.getContent())
            .createdAt(observation.getCreatedAt().toLocalDate())
            .authorName(observation.getAuthor().getName())
            .files(observationFiles.stream()
                .map(AnimalObservationFile::getFile)
                .map(FileResponse::from)
                .toList())
            .build();
    }
}
