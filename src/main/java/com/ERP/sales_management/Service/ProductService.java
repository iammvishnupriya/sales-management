package com.ERP.sales_management.Service;


import com.ERP.sales_management.DTO.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> getProductById(Integer productId);
}
