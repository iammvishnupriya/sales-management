package com.ERP.sales_management.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemRequest {
    @JsonProperty("product_id")
    @JsonAlias({"productId"})
    private Integer productId;
    
    private Integer quantity;
}
