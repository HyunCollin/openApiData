package com.api.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {

    private int statusCode;
    private String message;
    private Object data;

    @Builder
    ApiResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
