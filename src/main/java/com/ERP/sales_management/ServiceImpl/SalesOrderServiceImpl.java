package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private ProductRepository productRepository;
    @Autowired
    private  UserServiceClientImpl userServiceClient;

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

        for (SalesOrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SalesOrderItem orderItem = new SalesOrderItem();
            orderItem.setSalesOrder(salesOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(itemReq.getPrice());
            orderItems.add(orderItem);
        }

        salesOrderItemRepository.saveAll(orderItems);

        return new SuccessResponse<>(200,"Sales order created successfully", salesOrder);
    }

    @Override
    public SalesOrderDTO getOrderById(int id) {
        SalesOrder order = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
