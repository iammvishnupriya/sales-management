package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDTO> getProductById(Long productId);
}
