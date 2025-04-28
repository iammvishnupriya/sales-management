package com.ERP.sales_management.DTO;


import com.ERP.sales_management.Model.SalesOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class SalesOrderRequest {
    private List<SalesOrderItem> items;

    public List<SalesOrderItem> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItem> items) {
        this.items = items;
    }
}
