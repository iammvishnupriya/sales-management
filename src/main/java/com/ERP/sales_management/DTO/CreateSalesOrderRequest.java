package com.ERP.sales_management.DTO;

import lombok.*;

import java.util.List;

public class CreateSalesOrderRequest {
    private int customerId;
    private List<SalesOrderItemRequest> items;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<SalesOrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItemRequest> items) {
        this.items = items;
    }
}
