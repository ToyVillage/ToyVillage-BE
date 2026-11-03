package com.command.toyvillage_server.domain.web.faq.service;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import com.command.toyvillage_server.domain.web.faq.domain.repository.FaqRepository;
import com.command.toyvillage_server.domain.web.faq.exception.FaqNotFoundException;
import com.command.toyvillage_server.domain.web.faq.presentation.dto.request.FaqRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UpdateFaqService {
    private final FaqRepository faqRepository;

    @Transactional
    public void execute(FaqRequest request, Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> FaqNotFoundException.EXCEPTION);
        faq.update(request.getQuestionContent(), request.getQuestionAnswer());
        log.info("자주 묻는 질문 수정 / id : {}", faqId);
    }
}