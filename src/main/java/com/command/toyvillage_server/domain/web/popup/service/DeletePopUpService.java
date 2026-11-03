package com.command.toyvillage_server.domain.web.popup.service;

import com.command.toyvillage_server.domain.web.popup.domain.PopUp;
import com.command.toyvillage_server.domain.web.popup.domain.repository.PopUpRepository;
import com.command.toyvillage_server.domain.web.popup.exception.PopUpNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePopUpService {
    private final PopUpRepository popUpRepository;

    @Transactional
    public void execute(Long popUpId){
        PopUp popUp = popUpRepository.findById(popUpId)
                .orElseThrow(() -> PopUpNotFoundException.EXCEPTION);

        popUpRepository.delete(popUp);
    }
}
