package com.ERP.sales_management.Service;




import com.ERP.sales_management.Model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Integer id);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    OrderItem updateOrderItem(Integer id, OrderItem orderItem);

    void deleteOrderItem(Integer id);
}
