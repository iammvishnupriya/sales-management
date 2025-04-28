package com.ERP.sales_management.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemResponse {
    private Integer quantity;
    
    private Double price;
    
    private Double total;
    
    @JsonProperty("product_id")
    private Integer productId;
    
    @JsonProperty("product_name")
    private String productName;
    
    @JsonProperty("product_sku")
    private String productSku;
}