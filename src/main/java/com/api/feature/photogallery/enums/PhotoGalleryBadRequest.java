package com.api.feature.photogallery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhotoGalleryBadRequest {

    INVALID_REQUEST_PARAMETER_ERROR(10, "잘못된요청파라메터에러"),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR(11, "필수요청파라메터가없음"),
    TEMPORARILY_DISABLE_THE_SERVICE_KEY_ERROR(21, "일시적으로사용할수없는서비스키"),
    UNSIGNED_CALL_ERROR(33, "서명되지않은호출"),
    NODATA_ERROR(3, "데이터없음에러"),
    SERVICE_TIMEOUT_ERROR(5, "서비스연결실패에러"),
    DB_ERROR(2, "데이터베이스에러");

    private int code;
    private String message;

    public static boolean existErrorCode(int code) {
        for (PhotoGalleryBadRequest photoGalleryBadRequest : PhotoGalleryBadRequest.values()) {
            if (photoGalleryBadRequest.code == code) {
                return true;
            }
        }
        return false;
    }

    public static String  findErrorMessage(int code) {
        for (PhotoGalleryBadRequest photoGalleryBadRequest : PhotoGalleryBadRequest.values()) {
            if (photoGalleryBadRequest.code == code) {
                return photoGalleryBadRequest.message;
            }
        }
        return "-";
    }

}
