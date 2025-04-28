package com.ERP.sales_management.Repository;

import com.ERP.sales_management.Model.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {
    List<SalesOrderItem> findBySalesOrderId(Integer salesOrderId);
}
