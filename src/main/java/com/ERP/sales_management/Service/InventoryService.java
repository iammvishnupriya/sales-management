package com.ERP.sales_management.Service;


import com.ERP.sales_management.DTO.ProductDto;

public interface InventoryService {

    ProductDto getProductById(Integer productId);
}
