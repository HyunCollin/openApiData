package com.api.feature.photogallery.repository;

import com.api.feature.photogallery.entity.PhotoGallery;

import java.util.List;

public interface PhotoGalleryQuery {
    List<PhotoGallery> findBySearchKeyWordLike(String galTitle);
}
