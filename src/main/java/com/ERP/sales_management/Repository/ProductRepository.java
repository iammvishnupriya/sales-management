package com.ERP.sales_management.Repository;

import com.ERP.sales_management.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // You can add custom query methods here if needed
}
