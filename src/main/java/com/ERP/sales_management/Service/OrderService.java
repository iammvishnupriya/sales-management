package com.ERP.sales_management.Service;


import com.ERP.sales_management.Model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(Integer id);

    List<Order> getAllOrders();

    Order updateOrder(Integer id, Order order);

    void deleteOrder(Integer id);
}
