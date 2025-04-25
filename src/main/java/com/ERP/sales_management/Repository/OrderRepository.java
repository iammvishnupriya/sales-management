package com.ERP.sales_management.Repository;

import com.ERP.sales_management.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // You can add custom query methods here if needed
    // Example: List<Order> findByStatus(String status);
}
