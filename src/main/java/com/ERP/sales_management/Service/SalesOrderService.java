package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderResponse;

public interface SalesOrderService {    
    SalesOrderResponse createSalesOrder(CreateSalesOrderRequest createOrderRequest);
}