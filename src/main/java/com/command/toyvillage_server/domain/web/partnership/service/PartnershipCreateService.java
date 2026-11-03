package com.command.toyvillage_server.domain.web.partnership.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.partnership.domain.Partnership;
import com.command.toyvillage_server.domain.web.partnership.domain.repository.PartnershipRepository;
import com.command.toyvillage_server.domain.web.partnership.presentation.dto.request.PartnershipRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnershipCreateService {
    private final PartnershipRepository partnershipRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(PartnershipRequest partnershipRequest) {

        List<File> files = new ArrayList<>();
        if (partnershipRequest.getFileKeys() != null && !partnershipRequest.getFileKeys().isEmpty()) {
            files = fileRepository.findAllByFileKeyIn(partnershipRequest.getFileKeys());
            if (files.size() != partnershipRequest.getFileKeys().size()) {
                throw FileNotFoundException.EXCEPTION;
            }
        }

        Partnership partnership = Partnership.builder()
                .name(partnershipRequest.getName())
                .title(partnershipRequest.getTitle())
                .content(partnershipRequest.getContent())
                .email(partnershipRequest.getEmail())
                .phoneNumber(partnershipRequest.getPhoneNumber())
                .type(partnershipRequest.getPartnershipType())
                .files(files)
                .build();

        partnershipRepository.save(partnership);
        log.info("제휴 생성  {}", partnership.getId());
    }
}
