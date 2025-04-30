package com.ERP.sales_management.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalesOrderRequest {
    @JsonProperty("customer_id")
    @JsonAlias({"customerId"})
    private Integer customerId;
    
    @JsonProperty("order_date")
    @JsonAlias({"orderDate"})
    private String orderDate;
    
    @JsonProperty("total_amount")
    @JsonAlias({"totalAmount"})
    private Double totalAmount;
    
    private String status;
    
    @JsonProperty("order_items")
    @JsonAlias({"orderItems"})
    private List<OrderItemRequest> orderItems;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
