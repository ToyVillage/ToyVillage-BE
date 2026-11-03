package com.command.toyvillage_server.domain.web.popup.presentation;

import com.command.toyvillage_server.domain.web.popup.presentation.dto.request.PopUpRequest;
import com.command.toyvillage_server.domain.web.popup.presentation.dto.response.PopUpResponse;
import com.command.toyvillage_server.domain.web.popup.service.*;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/popup")
@RequiredArgsConstructor
public class PopUpController {
    private final CreatePopUpService createPopUpService;
    private final UpdatePopUpService updatePopUpService;
    private final DeletePopUpService deletePopUpService;
    private final QueryPopUpService queryPopUpService;
    private final QueryListPopUpService queryListPopUpService;

    @PostMapping
    public ResponseEntity<MessageResponse> createPopUp(@RequestBody @Valid PopUpRequest popUpRequest) {
        createPopUpService.execute(popUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("팝업이 생성되었습니다."));
    }

    @PatchMapping("/{popup-id}")
    public ResponseEntity<MessageResponse> updatePopUp(
            @RequestBody @Valid PopUpRequest popUpRequest,
            @PathVariable("popup-id") Long popUpId
    ) {
        updatePopUpService.execute(popUpRequest, popUpId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("팝업이 수정되었습니다."));
    }

    @DeleteMapping("/{popup-id}")
    public ResponseEntity<MessageResponse> deletePopUp(
            @PathVariable("popup-id")  Long popUpId
    ){
        deletePopUpService.execute(popUpId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("팝업이 삭제되었습니다."));
    }

    @GetMapping("/{popup-id}")
    public PopUpResponse getPopUp(
            @PathVariable("popup-id")   Long popUpId
    ){
        return queryPopUpService.execute(popUpId);
    }

    @GetMapping()
    public Page<PopUpResponse> getPopUps(Pageable pageable){
        return queryListPopUpService.execute(pageable);
    }
}
