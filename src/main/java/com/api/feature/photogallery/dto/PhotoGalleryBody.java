package com.api.feature.photogallery.dto;

import com.api.feature.photogallery.entity.PhotoGallery;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
public class PhotoGalleryBody {
    private LinkedHashMap<String, List<PhotoGallery>> items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
