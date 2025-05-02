package com.ERP.sales_management.Service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Response.SuccessResponse;

import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> getProductById(Integer productId);
    ResponseEntity<SuccessResponse<ProductDto>> createProduct(ProductDto dto);
    ResponseEntity<SuccessResponse<List<ProductDto>>> getAllProducts(Integer categoryId);


}
