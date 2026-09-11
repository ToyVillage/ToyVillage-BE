package com.command.toyvillage_server.domain.app.animal_manage.presentation;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalKindRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalObservationRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalLegalStatusRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryListResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalLegalStatusResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalObservationDetailResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalObservationListResponse;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalLegalStatusService;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalObservationService;
import com.command.toyvillage_server.domain.app.animal_manage.service.DeleteAnimalObservationService;
import com.command.toyvillage_server.domain.app.animal_manage.service.DeleteAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.DeleteAnimalLegalStatusService;
import com.command.toyvillage_server.domain.app.animal_manage.service.DeleteAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalLegalStatusListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalKindListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalManageListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalObservationListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalObservationService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalLegalStatusService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalObservationService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/animal-manage")
@RequiredArgsConstructor
public class AnimalManageController {
    private final CreateAnimalKindService createAnimalKindService;
    private final UpdateAnimalKindService updateAnimalKindService;
    private final QueryAnimalKindListService queryAnimalKindListService;
    private final QueryAnimalKindService queryAnimalKindService;
    private final CreateAnimalManageService createAnimalManageService;
    private final UpdateAnimalManageService updateAnimalManageService;
    private final QueryAnimalManageListService queryAnimalManageListService;
    private final QueryAnimalManageService queryAnimalManageService;
    private final CreateAnimalLegalStatusService createAnimalLegalStatusService;
    private final UpdateAnimalLegalStatusService updateAnimalLegalStatusService;
    private final QueryAnimalLegalStatusListService queryAnimalLegalStatusListService;
    private final CreateAnimalObservationService createAnimalObservationService;
    private final DeleteAnimalLegalStatusService deleteAnimalLegalStatusService;
    private final QueryAnimalObservationListService queryAnimalObservationListService;
    private final QueryAnimalObservationService queryAnimalObservationService;
    private final DeleteAnimalKindService deleteAnimalKindService;
    private final DeleteAnimalManageService deleteAnimalManageService;
    private final UpdateAnimalObservationService updateAnimalObservationService;
    private final DeleteAnimalObservationService deleteAnimalObservationService;

    @PostMapping("/kind")
    public ResponseEntity<MessageResponse> createAnimalKind(
        @RequestBody @Valid AnimalKindRequest request
    ) {
        createAnimalKindService.execute(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.of("종 생성 성공"));
    }

    @GetMapping("/kind")
    public AnimalKindQueryListResponse getAnimalKindList(
        @PageableDefault(
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        ) Pageable pageable,
        @RequestParam(value = "animalTaxonomic", required = false) AnimalTaxonomic animalTaxonomic
    ) {
        return queryAnimalKindListService.execute(animalTaxonomic, pageable);
    }

    @GetMapping("/kind/{animalKindId}")
    public AnimalKindQueryResponse getAnimalKindDetail(@PathVariable Long animalKindId) {
        return queryAnimalKindService.execute(animalKindId);
    }

    @PatchMapping("/kind/{animalKindId}")
    public MessageResponse updateAnimalKind(
        @PathVariable Long animalKindId,
        @RequestBody @Valid AnimalKindRequest request
    ) {
        updateAnimalKindService.execute(animalKindId, request);

        return MessageResponse.of("종 수정 성공");
    }

    @DeleteMapping("/kind/{animalKindId}")
    public MessageResponse deleteAnimalKind(@PathVariable Long animalKindId) {
        deleteAnimalKindService.execute(animalKindId);

        return MessageResponse.of("종 삭제 성공");
    }

    @GetMapping("/kind/{animalKindId}/animal")
    public Page<AnimalManageQueryListObjectResponse> getAnimalManageList(
        @PathVariable Long animalKindId,
        @PageableDefault(
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        ) Pageable pageable
    ) {
        return queryAnimalManageListService.execute(animalKindId, pageable);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createAnimalManage(
        @RequestBody @Valid AnimalManageRequest request
    ) {
        createAnimalManageService.execute(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.of("개체 생성 성공"));
    }

    @GetMapping("/{animalManageId}")
    public AnimalManageQueryResponse getAnimalManageDetail(@PathVariable Long animalManageId) {
        return queryAnimalManageService.execute(animalManageId);
    }

    @PatchMapping("/{animalManageId}")
    public MessageResponse updateAnimalManage(
        @PathVariable Long animalManageId,
        @RequestBody @Valid AnimalManageRequest request
    ) {
        updateAnimalManageService.execute(animalManageId, request);

        return MessageResponse.of("개체 수정 성공");
    }

    @DeleteMapping("/{animalManageId}")
    public MessageResponse deleteAnimalManage(@PathVariable Long animalManageId) {
        deleteAnimalManageService.execute(animalManageId);

        return MessageResponse.of("개체 삭제 성공");
    }

    @PostMapping("/legal-status")
    public ResponseEntity<MessageResponse> createAnimalLegalStatus(
        @RequestBody @Valid AnimalLegalStatusRequest request
    ) {
        createAnimalLegalStatusService.execute(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.of("법정지정분류 생성 성공"));
    }

    @GetMapping("/legal-status")
    public List<AnimalLegalStatusResponse> getAnimalLegalStatusList() {
        return queryAnimalLegalStatusListService.execute();
    }

    @PatchMapping("/legal-status/{animalLegalStatusId}")
    public MessageResponse updateAnimalLegalStatus(
        @PathVariable Long animalLegalStatusId,
        @RequestBody @Valid AnimalLegalStatusRequest request
    ) {
        updateAnimalLegalStatusService.execute(animalLegalStatusId, request);

        return MessageResponse.of("법정지정분류 수정 성공");
    }

    @DeleteMapping("/legal-status/{animalLegalStatusId}")
    public MessageResponse deleteAnimalLegalStatus(@PathVariable Long animalLegalStatusId) {
        deleteAnimalLegalStatusService.execute(animalLegalStatusId);

        return MessageResponse.of("법정지정분류 삭제 성공");
    }

    @PostMapping("/{animalManageId}/observations")
    public ResponseEntity<MessageResponse> createAnimalObservation(
        @PathVariable Long animalManageId,
        @RequestBody @Valid AnimalObservationRequest request
    ) {
        Long observationId = createAnimalObservationService.execute(animalManageId, request);

        return ResponseEntity
            .created(URI.create("/animal-manage/" + animalManageId + "/observations/" + observationId))
            .body(MessageResponse.of("관찰 및 특이사항 생성 성공"));
    }

    @GetMapping("/{animalManageId}/observations")
    public Page<AnimalObservationListResponse> getAnimalObservationList(
        @PathVariable Long animalManageId,
        @PageableDefault(
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        ) Pageable pageable
    ) {
        return queryAnimalObservationListService.execute(animalManageId, pageable);
    }

    @GetMapping("/{animalManageId}/observations/{observationId}")
    public AnimalObservationDetailResponse getAnimalObservation(
        @PathVariable Long animalManageId,
        @PathVariable Long observationId
    ) {
        return queryAnimalObservationService.execute(animalManageId, observationId);
    }

    @PatchMapping("/{animalManageId}/observations/{observationId}")
    public MessageResponse updateAnimalObservation(
        @PathVariable Long animalManageId,
        @PathVariable Long observationId,
        @RequestBody @Valid AnimalObservationRequest request
    ) {
        updateAnimalObservationService.execute(animalManageId, observationId, request);

        return MessageResponse.of("관찰 및 특이사항 수정 성공");
    }

    @DeleteMapping("/{animalManageId}/observations/{observationId}")
    public MessageResponse deleteAnimalObservation(
        @PathVariable Long animalManageId,
        @PathVariable Long observationId
    ) {
        deleteAnimalObservationService.execute(animalManageId, observationId);

        return MessageResponse.of("관찰 및 특이사항 삭제 성공");
    }
}
