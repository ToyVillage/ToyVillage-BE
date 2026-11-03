package com.command.toyvillage_server.domain.web.popup.service;


import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.popup.domain.PopUp;
import com.command.toyvillage_server.domain.web.popup.domain.repository.PopUpRepository;
import com.command.toyvillage_server.domain.web.popup.exception.PopUpNotFoundException;
import com.command.toyvillage_server.domain.web.popup.presentation.dto.request.PopUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePopUpService {
    private final PopUpRepository popUpRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(PopUpRequest popUpRequest, Long popUpId) {
        PopUp popUp = popUpRepository.findById(popUpId)
                .orElseThrow(() -> PopUpNotFoundException.EXCEPTION);

        File file = fileRepository.findByFileKey(popUpRequest.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION);

        popUp.update(
                file,
                popUpRequest.expirationDate(),
                popUpRequest.priority()
        );

        popUpRepository.save(popUp);
    }
}
