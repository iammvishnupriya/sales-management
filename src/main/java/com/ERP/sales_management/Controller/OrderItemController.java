package com.ERP.sales_management.Controller;


import com.ERP.sales_management.Model.OrderItem;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales_management/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<OrderItem>> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem created = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order item created successfully", created));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<OrderItem>>> getAllOrderItems() {
        List<OrderItem> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(new SuccessResponse<>(200,"All order items fetched", items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<OrderItem>> getOrderItemById(@PathVariable Long id) {
        OrderItem item = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order item fetched", item));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<SuccessResponse<List<OrderItem>>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> items = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order items for order ID fetched", items));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<OrderItem>> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        OrderItem updated = orderItemService.updateOrderItem(id, orderItem);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order item updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Order item deleted", "Deleted ID: " + id));
    }
}
