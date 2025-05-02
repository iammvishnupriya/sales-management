package com.ERP.sales_management.Repository;
import com.ERP.sales_management.Model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
    // List<SalesOrder> findByCustomerId(int customerId);
    java.util.Optional<SalesOrder> findByOrderNumber(String orderNumber);
    // List<SalesOrder> findByCustomerId(int customerId);
}
