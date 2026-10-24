package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.notice.exception.NoticeFcmNotSendException;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.events.NoticeCreated;
import com.command.toyvillage_server.domain.app.notification.domain.FcmToken;
import com.command.toyvillage_server.domain.app.notification.domain.repository.FcmTokenRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeNotificationService {
    private final FcmTokenRepository fcmTokenRepository;
    private final FirebaseMessaging firebaseMessaging;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void execute(NoticeCreated event) {
        List<String> tokens = event.teamIds() == null || event.teamIds().isEmpty()
            ? fcmTokenRepository.findAllByAppAdmin_DeleteStatusFalseAndAppAdmin_Role(AppAdminRole.EMPLOYEE)
                .stream().map(FcmToken::getToken).toList()
            : fcmTokenRepository.findTeamTokens(event.teamIds(), AppAdminRole.EMPLOYEE);

        sendBatch(event, tokens);
    }

    private void sendBatch(NoticeCreated event, List<String> tokens) {
        try {
            MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(Notification.builder()
                    .setTitle("새 공지사항")
                    .setBody(event.title())
                    .build())
                .putData("type", "NOTICE")
                .putData("noticeId", event.noticeId().toString())
                .build();

            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);

            List<String> invalidTokens = new ArrayList<>();

            for (int i = 0; i < response.getResponses().size(); i++) {
                SendResponse result = response.getResponses().get(i);

                if (!result.isSuccessful()) {
                    MessagingErrorCode errorCode = result.getException().getMessagingErrorCode();
                    if (errorCode == MessagingErrorCode.UNREGISTERED) {
                        invalidTokens.add(tokens.get(i));
                    }
                }
            }

            if (!invalidTokens.isEmpty()) {
                fcmTokenRepository.deleteAllByTokenIn(invalidTokens);
            }
        } catch (FirebaseMessagingException exception) {
            throw NoticeFcmNotSendException.EXCEPTION;
        }
    }
}
