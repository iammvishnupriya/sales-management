package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Service.InventoryService;
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
    private SalesOrderItemRepository salesOrderItemRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private InventoryService inventoryService;

    
    @Override
    @Transactional
    public SalesOrderResponse createSalesOrder(CreateSalesOrderRequest createOrderRequest) {
        
        SalesOrderResponse response = new SalesOrderResponse();

        String orderNumber = "ORD-" + (int)(Math.random() * 9000 + 1000);

        SalesOrder order = new SalesOrder();
            order.setOrderNumber(orderNumber);
            order.setCustomer(createOrderRequest.getCustomer()); 
            order.setCategoryId(createOrderRequest.getCategoryId());
            order.setProductName(createOrderRequest.getProductName());
            order.setQuantity(createOrderRequest.getQuantity());
            order.setOrderDate(LocalDate.now());
            order.setStatus(OrderStatus.PENDING);
            order.setTotalAmount(createOrderRequest.getAmount());

            SalesOrder savedOrder = salesOrderRepository.save(order);

            response.setOrderDate(savedOrder.getOrderDate().toString());
            response.setStatus(savedOrder.getStatus().toString());
            response.setTotalAmount(savedOrder.getTotalAmount());
        
        return response;
    }
}
