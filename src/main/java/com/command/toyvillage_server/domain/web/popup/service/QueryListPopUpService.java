package com.command.toyvillage_server.domain.web.popup.service;

import com.command.toyvillage_server.domain.web.popup.domain.repository.PopUpRepository;
import com.command.toyvillage_server.domain.web.popup.presentation.dto.response.PopUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryListPopUpService {
    private final PopUpRepository popUpRepository;

    @Transactional(readOnly = true)
    public Page<PopUpResponse> execute(Pageable p) {
        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        return popUpRepository.findAllByOrderByPriorityAsc(pageable)
                .map(PopUpResponse::from);
    }
}
