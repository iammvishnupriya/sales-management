package com.ERP.sales_management.Service;


import com.ERP.sales_management.DTO.UserResponseDto;

public interface UserServiceClient {
    UserResponseDto getUserById(int userId, String jwtToken);
}
