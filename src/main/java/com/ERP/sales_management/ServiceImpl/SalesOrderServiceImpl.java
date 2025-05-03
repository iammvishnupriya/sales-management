package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.*;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.*;
import com.ERP.sales_management.Repository.*;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.InventoryService;
import com.ERP.sales_management.Service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
            order.setPrice_per_unit(createOrderRequest.getPrice_per_unit());
            order.setOrderDate(LocalDate.now());
            order.setStatus(OrderStatus.PENDING);
            order.setTotalAmount(createOrderRequest.getAmount()!=null ? createOrderRequest.getAmount():0.0);

            SalesOrder savedOrder = salesOrderRepository.save(order);
            response.setPrice_per_unit(savedOrder.getPrice_per_unit());
            response.setOrderDate(savedOrder.getOrderDate().toString());
            response.setStatus(savedOrder.getStatus().toString());
            response.setTotalAmount(Double.valueOf(savedOrder.getTotalAmount()));
        
        return response;
    }


    @Override
    public ResponseEntity<SuccessResponse<List<SalesOrderDTO>>> getOrderByStatus(OrderStatus status) {
        List<SalesOrder> salesOrder;

        if(status != null){
            salesOrder = salesOrderRepository.findByStatus(status);
        } else {
            salesOrder = salesOrderRepository.findAll();
        }
        List<SalesOrderDTO> salesOrderDTOs = salesOrder.stream()
                    .map(order->
                    {
                        SalesOrderDTO salesOrderDTO = new SalesOrderDTO();
                        salesOrderDTO.setId(order.getId());
                        salesOrderDTO.setOrderNumber(order.getOrderNumber());
                        salesOrderDTO.setCustomerName(order.getCustomer());
                        salesOrderDTO.setStatus(order.getStatus().toString());
                        salesOrderDTO.setTotalAmount(Double.valueOf(order.getTotalAmount()));
                        return salesOrderDTO;
                    })
                    .collect(Collectors.toList());

        return ResponseEntity.ok(new SuccessResponse<>(200, "Orders fetched successfully", salesOrderDTOs));
    }
}
