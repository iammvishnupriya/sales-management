package com.ERP.sales_management.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalesOrderResponse {
    private Integer id;
    
    private String status;
    
    private String customer;
    
    @JsonProperty("order_date")
    private String orderDate;
    
    @JsonProperty("total_amount")
    private Double totalAmount;
    
    @JsonProperty("order_status")
    private String orderStatus;

    private Integer price_per_unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomerId(String customer) {
        this.customer = customer;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(Integer price_per_unit) {
        this.price_per_unit = price_per_unit;
    }     
}