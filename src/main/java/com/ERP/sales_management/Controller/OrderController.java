package com.ERP.sales_management.Controller;


import com.ERP.sales_management.Model.Order;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.OrderService;
import com.ERP.sales_management.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales_management/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<Order>> createOrder(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order created successfully", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order fetched successfully", order));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new SuccessResponse<>(200,"All orders fetched successfully", orders));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Order>> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updated = orderService.updateOrder(id, order);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order deleted successfully", "Order ID: " + id));
    }
}
