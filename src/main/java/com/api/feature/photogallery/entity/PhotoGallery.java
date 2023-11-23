package com.api.feature.photogallery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PhotoGallery {

    @Id
    private Long galContentId;

    private Long galContentTypeId;

    private String galTitle;

    private String galWebImageUrl;

    private Long galCreatedtime;

    private Long galModifiedtime;

    private Long galPhotographyMonth;

    private String galPhotographyLocation;

    private String galPhotographer;

    private String galSearchKeyword;

}
