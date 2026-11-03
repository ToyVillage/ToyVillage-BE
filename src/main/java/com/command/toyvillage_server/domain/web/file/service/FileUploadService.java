package com.command.toyvillage_server.domain.web.file.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileUploadResponse;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileUploadService {
    private final AwsS3Provider awsS3Provider;
    private final FileRepository fileRepository;

    @Transactional
    public FileUploadResponse execute(MultipartFile file) {
        String key = awsS3Provider.upload(file);

        try {
            File savedFile = fileRepository.saveAndFlush(
                File.builder()
                    .fileKey(key)
                    .fileName(StringUtils.getFilename(file.getOriginalFilename() == null ? "" : file.getOriginalFilename()))
                    .build()
            );
            log.info("파일{}", savedFile.getId());
            log.info("파일 업로드 완료 / key : {}", key);
        } catch (Exception e) {
            awsS3Provider.delete(key);
            throw e;
        }

        return FileUploadResponse.builder()
            .fileKey(key)
            .build();
    }
}
