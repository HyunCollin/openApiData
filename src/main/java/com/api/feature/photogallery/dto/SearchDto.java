package com.api.feature.photogallery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private int numOfRows;
    private int pageNo;
    private String mobileOS;
    private String mobileApp;
    private String arrange;
    private String type;

    @Builder
    SearchDto(int numOfRows, int pageNo, String mobileOS, String mobileApp, String arrange, String type) {
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.mobileOS = mobileOS;
        this.mobileApp = mobileApp;
        this.arrange = arrange;
        this.type = type;
    }
}

