package com.backend.ourstory.common.dto;

import com.backend.ourstory.common.entity.ApiExceptionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ApiResult<T> {


    private int result_cd;
    private String result_msg;
    private String detail_msg;

    private T data;
    private ApiExceptionEntity exception;

    @Builder
    public ApiResult(ResponseStatus status,String detail_msg,T data, ApiExceptionEntity exception) {
        this.result_cd = status.getResult_cd();
        this.result_msg = status.getResult_msg();
        this.detail_msg = detail_msg;
        this.data = data;
        this.exception = exception;
    }

    @Builder
    public ApiResult(ResponseStatus status,String detail_msg,T data) {
        this.result_cd = status.getResult_cd();
        this.result_msg = status.getResult_msg();
        this.detail_msg = detail_msg;
        this.data = data;
    }

}

