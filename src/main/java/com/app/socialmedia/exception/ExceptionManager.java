package com.app.socialmedia.exception;

import com.app.socialmedia.domain.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error("Error", e.getMessage()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> AppExceptionHandler(AppException e) {

        ErrorResult errorResult = new ErrorResult(e.getErrorCode(), e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error("ERROR", errorResult));
    }
}
