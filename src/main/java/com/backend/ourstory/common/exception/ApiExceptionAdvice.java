package com.backend.ourstory.common.exception;

import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.ExceptionEnum;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.entity.ApiExceptionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest request, final ApiException e) {

        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
                .errorCode(e.getError().getCode())
                .errorMessage(e.getError().getMessage())
                .build();

        //e.printStackTrace();

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiResult.builder()
                        .status(ResponseStatus.SERVER_ERROR)
                        .exception(apiExceptionEntity)
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest request, final RuntimeException e) {

        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
                .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiResult.builder()
                        .status(ResponseStatus.SERVER_ERROR)
                        .exception(apiExceptionEntity)
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {

        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
                .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiResult.builder()
                        .status(ResponseStatus.SERVER_ERROR)
                        .exception(apiExceptionEntity)
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest request, final Exception e) {

        ApiExceptionEntity apiExceptionEntity = ApiExceptionEntity.builder()
                .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorMessage(e.getMessage())
                .build();

        e.printStackTrace();

        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResult.builder()
                        .status(ResponseStatus.SERVER_ERROR)
                        .exception(apiExceptionEntity)
                        .build());
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResult<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((org.springframework.validation.FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        ApiResult<Map<String, String>> apiResult = ApiResult.<Map<String, String>>builder()
//                .status(ResponseStatus.FAILURE)
//                .detail_msg("Parameter Not Exisit")
//                .data(errors)
//                .build();
//
//        return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
//    }
}