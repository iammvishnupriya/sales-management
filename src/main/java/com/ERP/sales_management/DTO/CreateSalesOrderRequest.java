package com.ERP.sales_management.DTO;

import lombok.*;

@Data
public class CreateSalesOrderRequest {

    private String customer;
    private Integer categoryId;
    private String productName;
    private Integer quantity;
    private Double amount;
        
}
