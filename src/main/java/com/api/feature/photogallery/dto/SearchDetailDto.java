package com.api.feature.photogallery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDetailDto {
    private int numOfRows;
    private int pageNo;
    private String mobileOS;
    private String mobileApp;
    private String title;
    private String type;

    @Builder
    SearchDetailDto(int numOfRows, int pageNo, String mobileOS, String mobileApp, String title, String type) {
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.mobileOS = mobileOS;
        this.mobileApp = mobileApp;
        this.title = title;
        this.type = type;
    }
}

