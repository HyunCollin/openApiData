package com.api.feature.photogallery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoGalleryDto {

    private String galContentId;
    private String galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;
    private String galCreatedtime;
    private String galModifiedtime;
    private String galPhotographyMonth;
    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;
}
