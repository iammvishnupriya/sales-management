package com.ERP.sales_management.exception;

/**
 * Custom exception class for product-related errors
 */
public class ProductException extends RuntimeException {
    
    private final String errorCode;
    
    public ProductException(String message) {
        super(message);
        this.errorCode = "PRODUCT_ERROR";
    }
    
    public ProductException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public ProductException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PRODUCT_ERROR";
    }
    
    public ProductException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}