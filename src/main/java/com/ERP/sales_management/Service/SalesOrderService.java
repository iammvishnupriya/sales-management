package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderResponse;
import com.ERP.sales_management.Model.SalesOrder;

public interface SalesOrderService {
    SalesOrder createOrder(CreateSalesOrderRequest createOrderRequest);
    
    SalesOrderResponse createSalesOrder(CreateSalesOrderRequest createOrderRequest);
}