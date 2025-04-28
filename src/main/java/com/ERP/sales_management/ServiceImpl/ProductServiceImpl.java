package com.ERP.sales_management.ServiceImpl;



import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

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
}
