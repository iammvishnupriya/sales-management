package com.ERP.sales_management.Repository;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
    java.util.Optional<SalesOrder> findByOrderNumber(String orderNumber);

    List<SalesOrder> findByStatus(OrderStatus status);
}
