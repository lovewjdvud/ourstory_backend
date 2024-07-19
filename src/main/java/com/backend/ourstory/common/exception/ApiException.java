package com.backend.ourstory.common.exception;

import com.backend.ourstory.common.dto.ExceptionEnum;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ExceptionEnum error;

    public ApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}