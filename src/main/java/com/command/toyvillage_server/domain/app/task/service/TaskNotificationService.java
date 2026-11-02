package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.notification.domain.FcmToken;
import com.command.toyvillage_server.domain.app.notification.domain.repository.FcmTokenRepository;
import com.command.toyvillage_server.domain.app.task.exception.TaskFcmNotSendException;
import com.command.toyvillage_server.domain.app.task.presentation.dto.events.TaskNotification;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskNotificationService {
    private final FcmTokenRepository fcmTokenRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void execute(TaskNotification event) {
        if (event.recipientIds().isEmpty()) {
            return;
        }

        List<String> tokens = fcmTokenRepository.findAllByAppAdmin_IdInAndAppAdmin_DeleteStatusFalse(event.recipientIds())
                .stream().map(FcmToken::getToken).toList();

        if (!tokens.isEmpty()) {
            sendBatch(event, tokens);
        }
    }

    private void sendBatch(TaskNotification event, List<String> tokens) {
        try {
            String title;
            if (event.type() == TaskNotification.Type.TASK_ASSIGNED) {
                title = "새 업무지시";
            } else if (event.type() == TaskNotification.Type.TASK_COMPLETED) {
                title = "업무 완료 보고";
            } else if (event.type() == TaskNotification.Type.TASK_REJECTED) {
                title = "업무보고 반려";
            } else {
                title = "업무 승인 완료";
            }

            MulticastMessage.Builder builder = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(
                        Notification.builder()
                            .setTitle(title)
                            .setBody(event.title())
                            .build()
                    )
                    .putData("type", event.type().name())
                    .putData("taskId", event.taskId().toString());

            if (event.workReportId() != null) {
                builder.putData("workReportId", event.workReportId().toString());
            }

            BatchResponse response = firebaseMessaging.sendEachForMulticast(builder.build());

            List<String> invalidTokens = new ArrayList<>();

            for (int i = 0; i < response.getResponses().size(); i++) {
                SendResponse result = response.getResponses().get(i);
                if (!result.isSuccessful()
                        && result.getException().getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                    invalidTokens.add(tokens.get(i));
                }
            }

            if (!invalidTokens.isEmpty()) {
                fcmTokenRepository.deleteAllByTokenIn(invalidTokens);
            }
        } catch (FirebaseMessagingException exception) {
            throw TaskFcmNotSendException.EXCEPTION;
        }
    }
}
