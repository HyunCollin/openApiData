package com.api.core.exception.exception;

import com.api.core.enums.ApiExceptionEnum;
import com.api.feature.photogallery.dto.CmmMsgHeader;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private int statusCode;
    private String message;
    private Map<String, Object> data;

    public ApiException(){
    }
    public ApiException(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = new HashMap<>();
    }
    public ApiException(ApiExceptionEnum apiExceptionEnum) {
        this.statusCode = apiExceptionEnum.getCode();
        this.message = apiExceptionEnum.getMessage();
        this.data = new HashMap<>();
    }

    public ApiException(HttpStatus httpStatus, ApiExceptionEnum apiExceptionEnum) {
        this.statusCode = apiExceptionEnum.getCode();
        this.message = apiExceptionEnum.getMessage();
        this.data = new HashMap<>();
    }


    public ApiException(HttpStatus httpStatus, ApiExceptionEnum apiExceptionEnum, String data) {
        this.statusCode = apiExceptionEnum.getCode();
        this.message = apiExceptionEnum.getMessage();
        this.data = new HashMap<>();
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
        this.data = new HashMap<>();
    }

    public ApiException(CmmMsgHeader cmmMsgHeader){
        this.statusCode = cmmMsgHeader.getReturnReasonCode();
        this.message = cmmMsgHeader.getErrMsg() +", "+ cmmMsgHeader.getReturnAuthMsg();
    }
}
