package com.backend.ourstory.common.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {

    // System Exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),


    // Custom Exception
    SECURITY(HttpStatus.UNAUTHORIZED, "CE0001", "로그인이 필요합니다"),
    SEARCH_DATA_NULL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50","조회하신 데이터는 존재하지 않습니다");


    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
