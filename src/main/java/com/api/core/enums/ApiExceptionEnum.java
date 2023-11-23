package com.api.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionEnum {
    CHECK_API_KEY(400001, "check apiKey"),
    REQUIRED_GAL_TILE(400002, "GalTitle은 필수 값입니다."),
    REQUIRED_GAL_YEAR(400003, "Year는 필수 값입니다."),
    REQUIRED_GAL_MONTH(400004, "Month는 필수 값입니다."),
    INTERNAL_SERVER_ERROR(500001, "Internal Server Error"),
    API_SERVER_ERROR(500002, "관리자에게 문의해주세요.");

    private int code;
    private String message;

    ApiExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
