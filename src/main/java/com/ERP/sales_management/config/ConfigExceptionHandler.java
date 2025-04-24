package com.ERP.sales_management.config;



import com.ERP.sales_management.DTO.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * This class has been renamed to avoid conflict with the GlobalExceptionHandler in the exception package.
 * Consider using the more comprehensive handler in the exception package instead.
 */
@RestControllerAdvice
public class ConfigExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(400,ex.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, "Something went wrong");
        return ResponseEntity.status(500).body(error);
    }
}
