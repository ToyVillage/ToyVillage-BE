package com.command.toyvillage_server.domain.web.file.domain.repository;

import com.command.toyvillage_server.domain.web.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFileKey(String key);

    List<File> findAllByFileKeyIn(List<String> keys);

    @Query("SELECT f.fileKey FROM File f")
    public List<String> findAllFileKeys();
}
