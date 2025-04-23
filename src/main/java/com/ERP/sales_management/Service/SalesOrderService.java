package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderDTO;

import java.util.List;

public interface SalesOrderService {
    SalesOrderDTO createOrder(CreateSalesOrderRequest request, int userId);
    List<SalesOrderDTO> getOrdersByCustomer(int customerId);
    SalesOrderDTO getOrderById(int id);
}
