package com.ERP.sales_management.ServiceImpl;



import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Model.Category;
import com.ERP.sales_management.Model.Product;
import com.ERP.sales_management.Repository.CategoryRepository;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ERP.sales_management.Repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final WebClient webClient;

    @Value("${inventory.service.base-url}")
    private String inventoryBaseUrl;

    public ProductServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<ProductDto> getProductById(Integer productId) {
        return webClient.get()
                .uri(inventoryBaseUrl + "/api/product/" + productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

     @Override
     public ResponseEntity<SuccessResponse<ProductDto>> createProduct(ProductDto dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse<>(400, "Product data is null", null));
        }
        if (dto.getCategoryId() == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse<>(400, "Category ID is required", null));
        }

        Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new SuccessResponse<>(400, "Category not found", null));
        }

        String generatedSku = generateProductSku();

        Product product = new Product();
        product.setName(dto.getName());
        product.setSku(generatedSku);
        product.setPrice_per_unit(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(categoryOpt.get());

        Product saved = productRepository.save(product);

        ProductDto productDto = new ProductDto();
        productDto.setId(saved.getId());
        productDto.setName(saved.getName());
        productDto.setSku(generatedSku);
        productDto.setPrice(saved.getPrice_per_unit());
        productDto.setStockQuantity(saved.getStockQuantity());
        productDto.setCategoryId(saved.getCategory().getId());

        return ResponseEntity.ok(new SuccessResponse<>(200, "Product created", productDto));
    }

    private String generateProductSku() {
        Long count = productRepository.count(); // Get the current count of products
        long nextNumber = count + 1; // Next SKU number
    
        // Format to PC0001, PC0002, etc.
        return String.format("PC%04d", nextNumber);
    }

     @Override
    public ResponseEntity<SuccessResponse<List<ProductDto>>> getAllProducts(Integer categoryId) {
        List<Product> productList;

        if (categoryId != null) {
            productList = productRepository.findByCategoryId(categoryId);
            if (productList == null || productList.isEmpty()) {
                return ResponseEntity.ok(new SuccessResponse<>(200, "No products available for this category", Collections.emptyList()));
            }
        } else {
            productList = productRepository.findAll();
            if (productList == null || productList.isEmpty()) {
                return ResponseEntity.ok(new SuccessResponse<>(200, "No products available", Collections.emptyList()));
            }
        }

        List<ProductDto> products = productList.stream()
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId()!=null ? product.getId(): 0);
                    productDto.setName(product.getName()!=null ? product.getName(): "");
                    productDto.setSku(product.getSku()!= null ? product.getSku(): "");
                    productDto.setPrice(product.getPrice_per_unit()!=null ? product.getPrice_per_unit(): 0.0);
                    productDto.setStockQuantity(product.getStockQuantity()!=null? product.getStockQuantity(): 0);
                    if (product.getCategory() != null) {
                        productDto.setCategoryName(product.getCategory().getCategoryName());
                    }
                    return productDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(new SuccessResponse<>(200, "Products retrieved", products));
    }

}
