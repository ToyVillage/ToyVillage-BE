package com.command.toyvillage_server.domain.web.popup.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.popup.domain.PopUp;
import com.command.toyvillage_server.domain.web.popup.domain.repository.PopUpRepository;
import com.command.toyvillage_server.domain.web.popup.presentation.dto.request.PopUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePopUpService {
    private final PopUpRepository popUpRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(PopUpRequest popUpRequest) {
        File file = fileRepository.findByFileKey(popUpRequest.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION);

        PopUp popUp = PopUp.builder()
                .file(file)
                .expirationDate(popUpRequest.expirationDate())
                .priority(popUpRequest.priority())
                .build();

        popUpRepository.save(popUp);
    }
}
