package com.ERP.sales_management.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderDTO;
import com.ERP.sales_management.DTO.SalesOrderResponse;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Response.SuccessResponse;

public interface SalesOrderService {    
    SalesOrderResponse createSalesOrder(CreateSalesOrderRequest createOrderRequest);

    ResponseEntity<SuccessResponse<List<SalesOrderDTO>>> getOrderByStatus(OrderStatus status);
}