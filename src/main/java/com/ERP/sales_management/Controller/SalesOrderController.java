package com.ERP.sales_management.Controller;

import com.ERP.sales_management.DTO.CreateSalesOrderRequest;
import com.ERP.sales_management.DTO.SalesOrderResponse;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.SalesOrderService;
import com.ERP.sales_management.exception.CustomerNotFoundException;
import com.ERP.sales_management.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

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
                return ResponseEntity.badRequest().body(
                new SuccessResponse<>(400, "Customer ID is required", null)
            );
            }
            
            if (createOrderRequest.getOrderDate() == null) {
                return ResponseEntity.badRequest().body(
                    new SuccessResponse<>(400, "Order date is required", null)
                );
            }
            
            if (createOrderRequest.getOrderItems() == null || createOrderRequest.getOrderItems().isEmpty()) {
                return ResponseEntity.badRequest().body(
                    new SuccessResponse<>(400, "At least one order item is required", null)
                );
            }
            
            // Process the order
            SalesOrderResponse response = salesOrderService.createSalesOrder(createOrderRequest);
            
            SuccessResponse<SalesOrderResponse> successResponse = new SuccessResponse<>(
                201,
                "Sales order created successfully.",
                response
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (CustomerNotFoundException e) {
            System.err.println("Customer not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new SuccessResponse<>(404, e.getMessage(), null)
            );
        } catch (ProductException e) {
            System.err.println("Product not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new SuccessResponse<>(404, e.getMessage(), null)
            );
        } catch (DateTimeParseException e) {
            System.err.println("Date parse error: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                new SuccessResponse<>(400, "Invalid date format. Please use yyyy-MM-dd format.", null)
            );
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new SuccessResponse<>(500, "An error occurred: " + e.getMessage(), null)
            );
        }
    }
}
