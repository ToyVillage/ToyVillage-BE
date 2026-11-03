package com.command.toyvillage_server.domain.web.gallery.domain.repository;

import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    @Query(
        value = "select g from Gallery g join fetch g.file",
        countQuery = "select count(g) from Gallery g"
    )
    Page<Gallery> findAllWithFile(Pageable pageable);
}
