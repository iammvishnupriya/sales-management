package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderDTO;
import com.ERP.sales_management.Response.SuccessResponse;

import java.util.List;

public interface SalesOrderService {
    SalesOrderDTO getOrderById(int id);
    SuccessResponse<?> createSalesOrder(CreateSalesOrderRequest request, String token);
}
