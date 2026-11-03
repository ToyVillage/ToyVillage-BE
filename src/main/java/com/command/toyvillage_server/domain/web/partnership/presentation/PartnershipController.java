package com.command.toyvillage_server.domain.web.partnership.presentation;

import com.command.toyvillage_server.domain.web.partnership.presentation.dto.request.PartnershipRequest;
import com.command.toyvillage_server.domain.web.partnership.presentation.dto.response.PartnershipQueryAllResponse;
import com.command.toyvillage_server.domain.web.partnership.presentation.dto.response.PartnershipResponse;
import com.command.toyvillage_server.domain.web.partnership.service.PartnershipCreateService;
import com.command.toyvillage_server.domain.web.partnership.service.PartnershipQueryAllService;
import com.command.toyvillage_server.domain.web.partnership.service.PartnershipQueryService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnership")
@RequiredArgsConstructor
public class PartnershipController {
    private final PartnershipCreateService partnershipCreateService;
    private final PartnershipQueryAllService partnershipQueryAllService;
    private final PartnershipQueryService partnershipQueryService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid PartnershipRequest partnershipRequest) {
        partnershipCreateService.execute(partnershipRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("제휴생성이 완료되었습니다.")
                );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PartnershipQueryAllResponse> readAll(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return partnershipQueryAllService.execute(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PartnershipResponse readById(@PathVariable Long id) {
        return partnershipQueryService.execute(id);
    }
}
