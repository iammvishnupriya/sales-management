package com.ERP.sales_management.Controller;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.UserResponseDto;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.SalesOrderService;
import com.ERP.sales_management.Service.SalesService;
import com.ERP.sales_management.ServiceImpl.UserServiceClientImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales-management/api/sales-orders")

public class SalesController {

    private final SalesOrderService salesOrderService;
    private final UserServiceClientImpl userServiceClient;

    public SalesController(SalesOrderService salesOrderService, UserServiceClientImpl userServiceClient) {
        this.salesOrderService = salesOrderService;
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<?>> createSalesOrder(
            @RequestBody CreateSalesOrderRequest request,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(salesOrderService.createSalesOrder(request, token));
    }



}