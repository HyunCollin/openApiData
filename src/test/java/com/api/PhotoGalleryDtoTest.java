package com.api;

import com.api.core.enums.Arrange;
import com.api.core.enums.MobileOs;
import com.api.core.enums.ResponseType;
import com.api.feature.photogallery.dto.PhotoGalleryResponse;
import com.api.feature.photogallery.dto.SearchDto;
import com.api.feature.photogallery.service.PhotoGalleryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "loc")
public class PhotoGalleryDtoTest {

    @Autowired
    private PhotoGalleryService photoGalleryService;

    @Test
    public void collectPhotoGalleryListTest() {
        int numOfRows = 50;
        int pageNo = 0;
        String mobileOS = MobileOs.ETC.getCode();
        String mobileApp = "PhotoGalleryApp";
        String arrange = Arrange.GAL_CREATED_TIME.getCode();
        String type = ResponseType.JSON.getCode();

        SearchDto searchDto = SearchDto.builder()
                .numOfRows(numOfRows)
                .pageNo(pageNo)
                .mobileOS(mobileOS)
                .mobileApp(mobileApp)
                .arrange(arrange)
                .type(type)
                .build();

        PhotoGalleryResponse photoGalleryResponse = photoGalleryService.getPhotoGalleryList1(searchDto);

        if (photoGalleryResponse.isSuccess()) {
            Assertions.assertTrue(photoGalleryResponse.isSuccess());
            photoGalleryService.savePhotoGalleryItems(photoGalleryResponse.getItems());
        }
    }

    @Test
    public void getPhotoGalleryList1FailTest() {
        int numOfRows = 10;
        int pageNo = 0;
        String mobileOS = MobileOs.ETC.getCode();
        String mobileApp = "PhotoGalleryApp";
        String arrange = Arrange.GAL_CREATED_TIME.getCode();
        String type = ResponseType.JSON.getCode();

        SearchDto searchDto = SearchDto.builder()
                .numOfRows(numOfRows)
                .pageNo(pageNo)
                .mobileOS(mobileOS)
                .mobileApp(mobileApp)
                .arrange(arrange)
                .type(type)
                .build();

//        searchDto.setArrange("F");
        searchDto.setMobileOS(null);

        PhotoGalleryResponse photoGalleryResponse = photoGalleryService.getPhotoGalleryList1(searchDto);

        if (photoGalleryResponse.isSuccess()) {
            System.out.println("" + photoGalleryResponse.getHeader().getResultCode());
            System.out.println("" + photoGalleryResponse.getHeader().getResultMsg());
            System.out.println("" + photoGalleryResponse.getBody().getTotalCount());
            System.out.println("" + photoGalleryResponse.getBody().getNumOfRows());
            System.out.println("" + photoGalleryResponse.getBody().getPageNo());
            photoGalleryService.savePhotoGalleryItems(photoGalleryResponse.getItems());
        } else {
            System.out.println(photoGalleryResponse.getResponseResultCode());
            System.out.println(photoGalleryResponse.getResponseResultMsg());
        }
    }

}
