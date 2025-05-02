package com.ERP.sales_management.Controller;

import com.ERP.sales_management.DTO.CreateCustomerRequest;
import com.ERP.sales_management.DTO.CustomerDTO;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales_management/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    // Constructor-based dependency injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<CustomerDTO>> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerDTO customer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new SuccessResponse<>(201, "Customer created successfully", customer)
        );
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<SuccessResponse<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(
            new SuccessResponse<>(200, "Customers retrieved successfully", customers)
        );
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<CustomerDTO>> getCustomerById(@PathVariable int id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(
            new SuccessResponse<>(200, "Customer retrieved successfully", customer)
        );
    }

    // Update customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<CustomerDTO>> updateCustomer(@PathVariable int id,
                                                      @RequestBody CreateCustomerRequest request) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(
            new SuccessResponse<>(200, "Customer updated successfully", updatedCustomer)
        );
    }

    // Delete customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(
            new SuccessResponse<>(200, "Customer deleted successfully", "Customer with ID " + id + " has been deleted")
        );
    }
}
