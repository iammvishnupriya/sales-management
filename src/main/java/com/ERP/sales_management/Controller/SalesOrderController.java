package com.ERP.sales_management.Controller;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderDTO;
import com.ERP.sales_management.DTO.SalesOrderResponse;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.SalesOrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("sales_management/api/sales-orders")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateSalesOrderRequest createOrderRequest) {
        try {
            // Debug log
            System.out.println("Received order request: " + createOrderRequest);
            
            // Process the order
            SalesOrderResponse response = salesOrderService.createSalesOrder(createOrderRequest);
            
            SuccessResponse<SalesOrderResponse> successResponse = new SuccessResponse<>(
                200,
                "Sales order created successfully.",
                response
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        }  catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new SuccessResponse<>(500, "An error occurred: " + e.getMessage(), null)
            );
        }
    }

    @GetMapping("/getOrderByStatus")
    public ResponseEntity<SuccessResponse<List<SalesOrderDTO>>> getOrderByStatus(@RequestParam(required = false) OrderStatus status){
        System.out.println("Entering the controller");
        return salesOrderService.getOrderByStatus(status);
    }
}
