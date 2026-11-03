package com.command.toyvillage_server.domain.web.faq.service;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import com.command.toyvillage_server.domain.web.faq.domain.repository.FaqRepository;
import com.command.toyvillage_server.domain.web.faq.exception.FaqNotFoundException;
import com.command.toyvillage_server.domain.web.faq.presentation.dto.response.FaqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QueryFaqDetailService {
    private final FaqRepository faqRepository;

    @Transactional(readOnly = true)
    public FaqResponse execute(Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> FaqNotFoundException.EXCEPTION);

        return FaqResponse.from(faq);
    }
}
