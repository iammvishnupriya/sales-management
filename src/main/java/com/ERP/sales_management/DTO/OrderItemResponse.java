package com.ERP.sales_management.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemResponse {
    @JsonProperty("product_id")
    private Integer productId;
    
    private Integer quantity;
    
    private Double price;
    
    private Double total;
}