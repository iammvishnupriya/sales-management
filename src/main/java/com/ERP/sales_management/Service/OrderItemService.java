package com.ERP.sales_management.Service;




import com.ERP.sales_management.Model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    OrderItem updateOrderItem(Long id, OrderItem orderItem);

    void deleteOrderItem(Long id);
}
