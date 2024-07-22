package com.backend.ourstory.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {
    SUCCESS(20,"success"),
    FAILURE(40,"failure"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,50,"server failure");


    // 로그인 관련


//    ERROR(50,"Exception Error");

    private HttpStatus status;
    private final int result_cd;
    private final String result_msg;

    ResponseStatus(int result_cd, String result_msg) {
        this.result_cd = result_cd;
        this.result_msg = result_msg;
    }

    ResponseStatus(HttpStatus status, int result_cd, String result_msg) {
        this.status = status;
        this.result_cd = result_cd;
        this.result_msg = result_msg;
    }
}
