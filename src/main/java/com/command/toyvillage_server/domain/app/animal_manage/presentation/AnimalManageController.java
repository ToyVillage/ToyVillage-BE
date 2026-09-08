package com.command.toyvillage_server.domain.app.animal_manage.presentation;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalKindRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalLegalStatusRequest;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalLegalStatusResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryResponse;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalLegalStatusService;
import com.command.toyvillage_server.domain.app.animal_manage.service.CreateAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalLegalStatusListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalKindListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalManageListService;
import com.command.toyvillage_server.domain.app.animal_manage.service.QueryAnimalManageService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalKindService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalLegalStatusService;
import com.command.toyvillage_server.domain.app.animal_manage.service.UpdateAnimalManageService;
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
    public Page<AnimalKindQueryListObjectResponse> getAnimalKindList(
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
}
