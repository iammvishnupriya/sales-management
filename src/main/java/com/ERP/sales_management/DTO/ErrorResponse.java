package com.ERP.sales_management.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ErrorResponse {
    private int statusCode;
    private String statusMessage;

    public ErrorResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public ErrorResponse() {
    }
}
