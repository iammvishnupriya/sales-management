package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Service.SalesOrderService;
import com.ERP.sales_management.exception.CustomerNotFoundException;
import com.ERP.sales_management.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesOrderItemRepository salesOrderItemRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public SalesOrder createOrder(CreateSalesOrderRequest createOrderRequest) {
        try {
            // Debug log
            System.out.println("Processing order: " + createOrderRequest);
            
            // Create a new order
            SalesOrder salesOrder = new SalesOrder();
            
            // Find the customer
            if (createOrderRequest.getCustomerId() == null) {
                throw new IllegalArgumentException("Customer ID cannot be null");
            }
            
            System.out.println("Looking for customer with ID: " + createOrderRequest.getCustomerId());
            Customer customer = customerRepository.findById(createOrderRequest.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + createOrderRequest.getCustomerId()));
            
            System.out.println("Found customer: " + customer.getName());
            
            // Set the order properties
            salesOrder.setCustomer(customer);
            
            if (createOrderRequest.getOrderDate() == null) {
                throw new IllegalArgumentException("Order date cannot be null");
            }
            
            try {
                salesOrder.setOrderDate(LocalDate.parse(createOrderRequest.getOrderDate()));
            } catch (Exception e) {
                System.err.println("Error parsing date: " + createOrderRequest.getOrderDate());
                throw e;
            }
            
            salesOrder.setTotalAmount(createOrderRequest.getTotalAmount() != null ? 
                    createOrderRequest.getTotalAmount() : 0.0);
            
            // Generate a unique order number
            salesOrder.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8));
            
            // Set the status - handle case insensitivity and null values
            if (createOrderRequest.getStatus() != null) {
                String statusStr = createOrderRequest.getStatus().toUpperCase();
                try {
                    salesOrder.setStatus(OrderStatus.valueOf(statusStr));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status: " + statusStr + ", defaulting to PENDING");
                    // Default to PENDING if status is invalid
                    salesOrder.setStatus(OrderStatus.PENDING);
                }
            } else {
                // Default to PENDING if status is null
                System.out.println("Status is null, defaulting to PENDING");
                salesOrder.setStatus(OrderStatus.PENDING);
            }
            
            // The createdBy field is nullable, so we don't need to set it
            
            // Save the order first
            System.out.println("Saving order: " + salesOrder);
            SalesOrder savedOrder = salesOrderRepository.save(salesOrder);
            System.out.println("Order saved with ID: " + savedOrder.getId());
    
            // Process order items
            if (createOrderRequest.getOrderItems() == null || createOrderRequest.getOrderItems().isEmpty()) {
                throw new IllegalArgumentException("Order must have at least one item");
            }
            
            for (OrderItemRequest itemRequest : createOrderRequest.getOrderItems()) {
                if (itemRequest.getProductId() == null) {
                    throw new IllegalArgumentException("Product ID cannot be null for order item");
                }
                
                System.out.println("Looking for product with ID: " + itemRequest.getProductId());
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new ProductException("Product not found with ID: " + itemRequest.getProductId()));
                
                System.out.println("Found product: " + product.getName());
    
                SalesOrderItem orderItem = new SalesOrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.getQuantity() != null ? itemRequest.getQuantity() : 1);
                orderItem.setPrice(product.getPrice() != null ? product.getPrice() : 0.0);
                orderItem.setSalesOrder(savedOrder);
    
                System.out.println("Saving order item: " + orderItem);
                salesOrderItemRepository.save(orderItem);
                System.out.println("Order item saved");
            }
    
            return savedOrder;
        } catch (Exception e) {
            System.err.println("Error in createOrder: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    @Transactional
    public SalesOrderResponse createSalesOrder(CreateSalesOrderRequest createOrderRequest) {
        // First create the order using the existing method
        SalesOrder savedOrder = createOrder(createOrderRequest);
        
        // Then convert it to the response format
        SalesOrderResponse response = new SalesOrderResponse();
        response.setId(savedOrder.getId());
        response.setCustomerId(savedOrder.getCustomer().getId());
        response.setOrderDate(savedOrder.getOrderDate().toString());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setStatus(savedOrder.getStatus().toString().toLowerCase());
        response.setOrderStatus(savedOrder.getStatus().toString().toLowerCase());
        
        // Get all order items for this order
        List<SalesOrderItem> orderItems = salesOrderItemRepository.findBySalesOrderId(savedOrder.getId());
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        
        for (SalesOrderItem item : orderItems) {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setProductId(item.getProduct().getId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setTotal(item.getPrice() * item.getQuantity());
            
            orderItemResponses.add(itemResponse);
        }
        
        response.setOrderItems(orderItemResponses);
        
        return response;
    }
}
