package com.api.feature.photogallery.dto;

import com.api.feature.photogallery.entity.PhotoGallery;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
public class PhotoGalleryResponse {
    private LinkedHashMap<String, Object> response = new LinkedHashMap<>();


    public void putData(String key, Object object) {
        this.response.put(key, object);
    }

    public PhotoGalleryHeader getHeader() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (this.response.containsKey("header")) {
            return objectMapper.convertValue(this.response.get("header"), PhotoGalleryHeader.class);
        } else {
            return new PhotoGalleryHeader();
        }
    }

    public PhotoGalleryBody getBody() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (this.response.containsKey("body")) {
            return objectMapper.convertValue(this.response.get("body"), PhotoGalleryBody.class);
        } else {
            return new PhotoGalleryBody();
        }
    }

    public boolean isSuccess() {
        return "0000".equals(getHeader().getResultCode()) && "OK".equals(getHeader().getResultMsg());
    }

    public List<PhotoGallery> getItems() {
        return getBody().getItems().get("item");
    }

    public String getResponseResultCode(){
        return this.response.get("resultCode") == null ? "" : String.valueOf(this.response.get("resultCode"));
    }

    public String getResponseResultMsg(){
        return this.response.get("resultMsg") == null ? "" : String.valueOf(this.response.get("resultMsg"));
    }
}
