package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepo;
    @Autowired
    private SalesOrderItemRepository itemRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;

    @Override
    public SalesOrderDTO createOrder(CreateSalesOrderRequest request, int userId) {
        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SalesOrder order = new SalesOrder();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setCreatedBy(user);
        order.setTotalAmount(0.0);

        order = salesOrderRepo.save(order);

        double total = 0.0;
        for (SalesOrderItemRequest itemReq : request.getItems()) {
            Product product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SalesOrderItem item = new SalesOrderItem();
            item.setSalesOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(itemReq.getPrice());

            itemRepo.save(item);
            total += itemReq.getQuantity() * itemReq.getPrice();
        }

        order.setTotalAmount(total);
        salesOrderRepo.save(order);

        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(customer.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().toString());
        dto.setTotalAmount(total);
        dto.setCreatedById(user.getId());

        return dto;
    }

    @Override
    public List<SalesOrderDTO> getOrdersByCustomer(int customerId) {
        List<SalesOrder> orders = salesOrderRepo.findByCustomerId(customerId);
        List<SalesOrderDTO> dtos = new ArrayList<>();

        for (SalesOrder order : orders) {
            SalesOrderDTO dto = new SalesOrderDTO();
            dto.setId(order.getId());
            dto.setOrderNumber(order.getOrderNumber());
            dto.setCustomerId(order.getCustomer().getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setStatus(order.getStatus().toString());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setCreatedById(order.getCreatedBy().getId());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public SalesOrderDTO getOrderById(int id) {
        SalesOrder order = salesOrderRepo.findById(id)
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
