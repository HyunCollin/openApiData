package com.api.core.exception.handler;

import com.api.core.exception.exception.ApiException;
import com.api.core.domain.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ApiResponse handleException(Exception e) {
        log.error(e.getMessage());
        return ApiResponse.builder()
            .statusCode(INTERNAL_SERVER_ERROR.value())
            .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ApiResponse handleMethodArgumentTypeMismatchException(Exception e) {
        return ApiResponse.builder()
            .statusCode(BAD_REQUEST.value())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler({ApiException.class})
    public ApiResponse handleApiException(ApiException e) {
        return ApiResponse.builder()
            .statusCode(e.getStatusCode())
            .message(e.getMessage())
            .data(e.getData())
            .build();
    }


}
