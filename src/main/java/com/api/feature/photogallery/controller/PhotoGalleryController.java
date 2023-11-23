package com.api.feature.photogallery.controller;

import com.api.core.domain.ApiResponse;
import com.api.feature.photogallery.dto.PhotoGalleryResponse;
import com.api.feature.photogallery.dto.SearchDto;
import com.api.feature.photogallery.entity.PhotoGallery;
import com.api.feature.photogallery.service.PhotoGalleryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class PhotoGalleryController {

    private PhotoGalleryService photoGalleryService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @Operation(summary = "photo-gallery", description = "관광사진갤러리 목록 조회 후 DB 저장")
    @PutMapping(value = "/api/v1/collect/photo/gallery")
    public ApiResponse getPhotoGallery(SearchDto searchDto) {
        PhotoGalleryResponse photoGalleryResponse = photoGalleryService.getPhotoGalleryList1(searchDto);
        int createCount = photoGalleryService.savePhotoGalleryItems(photoGalleryResponse.getItems());
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(String.format("%s개 추가되었습니다.", createCount))
                .build();
    }


    @Operation(summary = "photo-gallery", description = "SearchKeyWord like 검색")
    @GetMapping(value = "/api/v1/search/photo/gallery/title")
    public ApiResponse searchPhotoGallery(String searchKeyword) {
        List<PhotoGallery> photoGalleries = photoGalleryService.findBySearchKeyWordLike(searchKeyword);
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(photoGalleries)
                .build();
    }

    @Operation(summary = "photo-gallery", description = "사진 촬영 년월 조회")
    @GetMapping(value = "/api/v1/search/photo/gallery/years/{year}/month/{mm}")
    public ApiResponse searchPhotoGalleryByYyyyMm(@PathVariable String year, @PathVariable String mm) {
        List<PhotoGallery> photoGalleries = photoGalleryService.searchPhotoGalleryByYyyyMm(year, mm);
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(photoGalleries)
                .build();
    }

    @Operation(summary = "photo-gallery", description = "사진 촬영 년월 조회 ( page )")
    @GetMapping(value = "/api/v1/search/photo/gallery/page/years/{year}/month/{mm}")
    public ApiResponse searchPhotoGalleryByYyyyMm(@PathVariable String year, @PathVariable String mm, int numOfRows, int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo, numOfRows);
        List<PhotoGallery> photoGalleries = photoGalleryService.searchPhotoGalleryByYyyyMm(year, mm, pageRequest);
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(photoGalleries)
                .build();
    }

}
