package com.command.toyvillage_server.domain.web.animal.presentation;

import com.command.toyvillage_server.domain.web.animal.presentation.dto.request.AnimalRequest;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.response.AnimalListResponse;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.response.AnimalResponse;
import com.command.toyvillage_server.domain.web.animal.service.*;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
public class AnimalController {
    private final CreateAnimalService createAnimalService;
    private final DeleteAnimalService deleteAnimalService;
    private final UpdateAnimalService updateAnimalService;
    private final QueryAnimalService queryAnimalService;
    private final QueryAllAnimalService queryAllAnimalService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@ModelAttribute @Valid AnimalRequest request) {
        createAnimalService.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("동물소개가 생성되었습니다."));
    }

    @PutMapping("/{animal_id}")
    public ResponseEntity<MessageResponse> update(@ModelAttribute @Valid AnimalRequest request,  @PathVariable Long animal_id) {
        updateAnimalService.execute(request, animal_id);

        return ResponseEntity
                .ok(MessageResponse.of("동물소개가 수정되었습니다."));
    }

    @DeleteMapping("/{animal_id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long animal_id) {
        deleteAnimalService.execute(animal_id);

        return ResponseEntity
                .ok(MessageResponse.of("동물소개가 삭제되었습니다."));
    }

    @GetMapping("/{animal_id}")
    public AnimalResponse query(@PathVariable Long animal_id) {
        return queryAnimalService.execute(animal_id);
    }

    @GetMapping
    public AnimalListResponse queryAll() {
        return queryAllAnimalService.execute();
    }
}
