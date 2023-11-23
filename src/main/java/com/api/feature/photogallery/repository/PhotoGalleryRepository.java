package com.api.feature.photogallery.repository;

import com.api.feature.photogallery.entity.PhotoGallery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery, Long>, PhotoGalleryQuery {

    List<PhotoGallery> findByGalPhotographyMonth(Long galPhotographyMonth);
    List<PhotoGallery> findByGalPhotographyMonth(Long galPhotographyMonth, PageRequest pageRequest);

}
