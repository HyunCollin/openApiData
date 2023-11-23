package com.api.feature.photogallery.service;

import com.api.core.enums.ApiExceptionEnum;
import com.api.core.exception.exception.ApiException;
import com.api.feature.photogallery.dto.CmmMsgHeader;
import com.api.feature.photogallery.dto.OpenApiServiceResponse;
import com.api.feature.photogallery.dto.PhotoGalleryResponse;
import com.api.feature.photogallery.dto.SearchDto;
import com.api.feature.photogallery.entity.PhotoGallery;
import com.api.feature.photogallery.enums.PhotoGalleryBadRequest;
import com.api.feature.photogallery.enums.PhotoGalleryUrl;
import com.api.feature.photogallery.repository.PhotoGalleryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class PhotoGalleryService {

    @Value("${open.data.api.key}")
    private String apiKey;

    @Value("${open.data.api.key.encode}")
    private String encodeApiKey;

    private PhotoGalleryRepository photoGalleryRepository;

    public PhotoGalleryService(PhotoGalleryRepository photoGalleryRepository) {
        this.photoGalleryRepository = photoGalleryRepository;
    }

    public PhotoGalleryResponse getPhotoGalleryList1(SearchDto searchDto) {
        PhotoGalleryResponse photoGalleryResponse = new PhotoGalleryResponse();
        String mainUrl = PhotoGalleryUrl.GALLERY_LIST1.getUrl();
        String responseBody = null;
        try {
            UriComponents uri = UriComponentsBuilder
                    .fromHttpUrl(mainUrl)
                    .queryParam("numOfRows", searchDto.getNumOfRows())
                    .queryParam("pageNo", searchDto.getPageNo())
                    .queryParam("MobileOS", searchDto.getMobileOS())
                    .queryParam("MobileApp", searchDto.getMobileApp())
                    .queryParam("arrange", searchDto.getArrange())
                    .queryParam("_type", searchDto.getType())
                    .queryParam("serviceKey", encodeApiKey)
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri.toUriString()))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            log.info(responseBody);

            if (StringUtils.hasText(responseBody) && responseBody.contains("errMsg")) {
                throwExceptionOpenApiError(responseBody);
            }
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ApiExceptionEnum.INTERNAL_SERVER_ERROR);
            }

            Gson gson = new Gson();
            LinkedHashMap<String, Object> resBody = gson.fromJson(responseBody, LinkedHashMap.class);
            if (resBody.get("resultCode") != null) {
                int resultCode = Integer.parseInt((String) resBody.get("resultCode"));
                throw new ApiException(HttpStatus.BAD_REQUEST, PhotoGalleryBadRequest.findErrorMessage(resultCode));
            }
            photoGalleryResponse = gson.fromJson(responseBody, PhotoGalleryResponse.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error("getPhotoGalleryList1 Error {} response {}", e.getMessage(), responseBody);
            throw new ApiException(ApiExceptionEnum.API_SERVER_ERROR);
        }
        return photoGalleryResponse;
    }

    private void throwExceptionOpenApiError(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OpenApiServiceResponse openApiServiceResponse = objectMapper.readValue(responseBody, OpenApiServiceResponse.class);
        CmmMsgHeader cmmMsgHeader = openApiServiceResponse.getCmmMsgHeader();
        throw new ApiException(cmmMsgHeader);
    }

    @Transactional
    public int savePhotoGalleryItems(List<PhotoGallery> PhotoGalleries) {
        return photoGalleryRepository.saveAll(PhotoGalleries).size();
    }

    public List<PhotoGallery> findBySearchKeyWordLike(String searchKeyword) {
        if (!StringUtils.hasText(searchKeyword)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiExceptionEnum.REQUIRED_GAL_TILE);
        }
        return photoGalleryRepository.findBySearchKeyWordLike(searchKeyword);
    }

    public List<PhotoGallery> searchPhotoGalleryByYyyyMm(String year, String mm) {
        validateYearMonth(year, mm);
        Long galPhotographyMonth = Long.valueOf(year + mm);
        return photoGalleryRepository.findByGalPhotographyMonth(galPhotographyMonth);
    }

    public List<PhotoGallery> searchPhotoGalleryByYyyyMm(String year, String mm, PageRequest pageRequest) {
        validateYearMonth(year, mm);
        Long galPhotographyMonth = Long.valueOf(year + mm);
        return photoGalleryRepository.findByGalPhotographyMonth(galPhotographyMonth, pageRequest);
    }

    private void validateYearMonth(String year, String mm) {
        if (!StringUtils.hasText(year)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiExceptionEnum.REQUIRED_GAL_TILE);
        } else if (!StringUtils.hasText(mm)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiExceptionEnum.REQUIRED_GAL_TILE);
        }
    }
}
