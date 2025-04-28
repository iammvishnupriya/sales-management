package com.ERP.sales_management.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesOrderResponse {
    private Integer id;
    
    @JsonProperty("customer_id")
    private Integer customerId;
    
    @JsonProperty("order_date")
    private String orderDate;
    
    @JsonProperty("total_amount")
    private Double totalAmount;
    
    private String status;
    
    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;
    
    @JsonProperty("order_status")
    private String orderStatus;
}