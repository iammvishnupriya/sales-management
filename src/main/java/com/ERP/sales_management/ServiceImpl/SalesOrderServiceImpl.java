package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.PaymentService;
import com.ERP.sales_management.Service.ProductService;
import com.ERP.sales_management.Service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private SalesOrderItemRepository salesOrderItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserServiceClientImpl userServiceClient;
    @Autowired
    private ProductService productService;  // Injecting ProductClient

    @Override
    public SuccessResponse<?> createSalesOrder(CreateSalesOrderRequest request, String token) {
        // Verify user from user_management
        UserResponseDto user = userServiceClient.getUserById(request.getCustomerId(), token);

        // Validate customer
        Optional<Customer> optionalCustomer = customerRepository.findById(request.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = optionalCustomer.get();

        // Create sales order
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(customer);
        salesOrder.setId(user.getId());
        salesOrder.setOrderDate(LocalDate.from(LocalDateTime.now()));
        salesOrder.setStatus(OrderStatus.PLACED);

        salesOrder = salesOrderRepository.save(salesOrder);

        List<SalesOrderItem> orderItems = new ArrayList<>();

        // Loop over items and fetch product details from Inventory service
        for (SalesOrderItemRequest itemReq : request.getItems()) {
            // Call Inventory service to get product details
            ProductDTO productDTO = productService.getProductById(itemReq.getProductId()).block(); // Synchronous call
            if (productDTO == null) {
                throw new RuntimeException("Product not found from inventory service");
            }

            // Map ProductDTO to SalesOrder's Product model (you can modify this mapping logic as needed)
            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setSku(productDTO.getSku());
            product.setPrice(productDTO.getPrice());  // Assuming price is returned in the ProductDTO

            // Create SalesOrderItem
            SalesOrderItem orderItem = new SalesOrderItem();
            orderItem.setSalesOrder(salesOrder);
            orderItem.setProduct(product);  // Set the product fetched from Inventory
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice()); // Use product price from Inventory
            orderItems.add(orderItem);
        }

        // Save all order items
        salesOrderItemRepository.saveAll(orderItems);

        // Return Success Response
        return new SuccessResponse<>(200, "Sales order created successfully", salesOrder);
    }

    @Override
    public SalesOrderDTO getOrderById(int id) {
        // Fetch sales order by ID
        SalesOrder order = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Map SalesOrder to SalesOrderDTO
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().toString());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedById(order.getCreatedBy().getId());

        return dto;
    }
}
