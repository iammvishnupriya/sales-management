package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.UserResponseDto;

public interface SalesService {

    void createSalesOrder(int userId);
    UserResponseDto getUserById(int userId, String jwtToken);
}
