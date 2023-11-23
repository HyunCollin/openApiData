package com.api.feature.photogallery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhotoGalleryUrl {

    GALLERY_LIST1("https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1"),
    GALLERY_DETAIL_LIST1("https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryDetailList1"),
    GALLERY_SYNC_DETAIL_LIST1("https://apis.data.go.kr/B551011/PhotoGalleryService1/gallerySyncDetailList1"),
    GALLERY_SEARCH_LIST1("https://apis.data.go.kr/B551011/PhotoGalleryService1/gallerySearchList1");
    private String url;
}
