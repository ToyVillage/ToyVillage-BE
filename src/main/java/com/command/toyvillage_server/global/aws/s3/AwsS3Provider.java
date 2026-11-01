package com.command.toyvillage_server.global.aws.s3;

import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.global.aws.s3.exception.FileDeleteFailException;
import com.command.toyvillage_server.global.aws.s3.exception.FileEmptyException;
import com.command.toyvillage_server.global.aws.s3.exception.FileUploadFailException;
import com.command.toyvillage_server.global.aws.s3.exception.KeyEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class AwsS3Provider {
    private static final int MAX_DOWNLOAD_BYTES = 10 * 1024 * 1024;
    private final S3Client s3Client;
    private final FileRepository fileRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw FileEmptyException.EXCEPTION;
        }

        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .contentType(file.getContentType())
            .key(key)
            .build();
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException | SdkException e) {
            log.error("파일 업로드 실패", e);
            log.error("파일 업로드 실패 / 메시지 : {}", e.getMessage());
            try {
                delete(key);
            } catch (Exception e1) {
                log.error("파일업로드 실패 후 삭제 작업 실패", e1);
            }
            throw FileUploadFailException.EXCEPTION;
        }

        return key;
    }

    public void delete(String key) {
        if(key == null || key.isBlank()) {
            throw KeyEmptyException.EXCEPTION;
        }

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

            fileRepository.findByFileKey(key).ifPresent(fileRepository::delete);
            s3Client.deleteObject(deleteObjectRequest);
        } catch (RuntimeException e) {
            log.error("파일 삭제 실패", e);
            log.error("파일 삭제 실패 / 메시지 : {}", e.getMessage());
            throw FileDeleteFailException.EXCEPTION;
        }
    }
    

    public byte[] download(String key) {
        try (InputStream stream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build())) {
            byte[] bytes = stream.readNBytes(MAX_DOWNLOAD_BYTES + 1);
            if (bytes.length > MAX_DOWNLOAD_BYTES) {
                log.warn("파일 다운로드 크기 초과 / key : {}", key);
                return null;
            }
            return bytes;
        } catch (IOException | RuntimeException e) {
            log.warn("파일 다운로드 실패 / key : {}", key, e);
            return null;
        }
    }
}
