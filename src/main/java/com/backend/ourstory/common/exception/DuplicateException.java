package com.backend.ourstory.common.exception;

import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DuplicateException extends RuntimeException {
    private ResponseStatus responseStatus;
    private String customMessage;
    public DuplicateException(ResponseStatus e, String customMessage) {
        super(e.getResult_msg());
        this.responseStatus = e;
        this.customMessage = customMessage;
    }

}
