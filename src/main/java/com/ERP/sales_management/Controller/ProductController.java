package com.ERP.sales_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.ProductService;
import java.util.*;

@RestController
@RequestMapping("/sales_management/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<ProductDto>> createProduct(@RequestBody ProductDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<SuccessResponse<List<ProductDto>>> getAllProducts(@RequestParam(required=false) Integer categoryId) {
        return productService.getAllProducts(categoryId);
    }

}
