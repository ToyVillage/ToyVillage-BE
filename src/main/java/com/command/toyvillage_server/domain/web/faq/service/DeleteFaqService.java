package com.command.toyvillage_server.domain.web.faq.service;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import com.command.toyvillage_server.domain.web.faq.domain.repository.FaqRepository;
import com.command.toyvillage_server.domain.web.faq.exception.FaqNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeleteFaqService {
    private final FaqRepository faqRepository;

    @Transactional
    public void execute(Long faqId) {
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> FaqNotFoundException.EXCEPTION);

        log.info("자주 묻는 질문 삭제 / id : {}", faqId);
        faqRepository.delete(faq);
    }
}
