package com.ERP.sales_management.Controller;

import com.ERP.sales_management.DTO.ApiResponse;
import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderResponse;
import com.ERP.sales_management.Service.SalesOrderService;
import com.ERP.sales_management.exception.CustomerNotFoundException;
import com.ERP.sales_management.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

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
            
            // Validate request
            if (createOrderRequest.getCustomerId() == null) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Customer ID is required"));
            }
            
            if (createOrderRequest.getOrderDate() == null) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Order date is required"));
            }
            
            if (createOrderRequest.getOrderItems() == null || createOrderRequest.getOrderItems().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("At least one order item is required"));
            }
            
            // Process the order
            System.out.println("Entering the controller");
        SalesOrderResponse response = salesOrderService.createSalesOrder(createOrderRequest);
            
            ApiResponse<SalesOrderResponse> apiResponse = ApiResponse.success(
                "Sales order created successfully.", 
                response
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (CustomerNotFoundException e) {
            System.err.println("Customer not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        } catch (ProductException e) {
            System.err.println("Product not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        } catch (DateTimeParseException e) {
            System.err.println("Date parse error: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Invalid date format. Please use yyyy-MM-dd format."));
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An error occurred: " + e.getMessage()));
        }
    }
}
