package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Service.InventoryService;
import com.ERP.sales_management.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${inventory.service.base-url:http://localhost:8084/inventory_management}")
    private String inventoryBaseUrl;
    
    // These should be configured in application.properties
    @Value("${inventory.service.username:admin}")
    private String username;
    
    @Value("${inventory.service.password:admin}")
    private String password;
    
    @Value("${inventory.service.api-key:}")
    private String apiKey;
    
    @Autowired
    public InventoryServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    
    @Override
    public ProductDto getProductById(Integer productId) {
        try {
            System.out.println("Fetching product from inventory management: " + productId);
            System.out.println("Using inventory base URL: " + inventoryBaseUrl);
            
            try {
                // Create a WebClient with authentication
                WebClient webClient = webClientBuilder.build();
                
                String uri = inventoryBaseUrl + "/api/product/get/" + productId;
                System.out.println("Making request to: " + uri);
                
                // Get the JWT token from the current request
                String jwtToken = extractJwtTokenFromRequest();
                System.out.println("Using JWT token: " + (jwtToken != null ? "Present" : "Not present"));
                
                // Prepare the WebClient request
                WebClient.RequestHeadersSpec<?> requestSpec = webClient
                    .get()
                    .uri(uri)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                
                // Add authorization header - prefer JWT token if available, otherwise use basic auth
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    requestSpec = requestSpec.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
                } else {
                    System.out.println("No JWT token found, falling back to basic auth");
                    requestSpec = requestSpec.header(HttpHeaders.AUTHORIZATION, createBasicAuthHeader(username, password));
                }
                
                // Execute the request
                Map<String, Object> response = requestSpec
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
                
                System.out.println("Response from inventory service: " + response);
                
                // Check for the expected response structure based on the SuccessResponse class
                if (response != null && response.containsKey("data")) {
                    Map<String, Object> productData = (Map<String, Object>) response.get("data");
                    
                    // Create a new ProductDto from the response
                    ProductDto productDto = new ProductDto();
                    productDto.setId(productId);
                    productDto.setName((String) productData.get("name"));
                    productDto.setPrice(Double.valueOf(productData.get("price").toString()));
                    productDto.setSku((String) productData.getOrDefault("sku", ""));
                    
                    return productDto;
                } else {
                    throw new ProductException("Product not found in inventory management");
                }
            } catch (WebClientResponseException e) {
                System.err.println("Error fetching product from inventory: " + e.getMessage());
                System.err.println("Response status: " + e.getStatusCode());
                System.err.println("Response body: " + e.getResponseBodyAsString());
                
                // For demo purposes only - create a mock product if we can't connect to the inventory service
                if (e.getStatusCode().value() == 403 || e.getStatusCode().value() == 401) {
                    System.out.println("WARNING: Creating mock product data for demo purposes!");
                    ProductDto mockProduct = new ProductDto();
                    mockProduct.setId(productId);
                    mockProduct.setName("Mock Product " + productId);
                    mockProduct.setPrice(99.99);
                    mockProduct.setSku("MOCK-" + productId);
                    return mockProduct;
                }
                
                throw new ProductException("Failed to fetch product from inventory: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error when calling inventory service: " + e.getClass().getName() + ": " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("Connection refused")) {
                    System.out.println("WARNING: Creating mock product data for demo purposes!");
                    ProductDto mockProduct = new ProductDto();
                    mockProduct.setId(productId);
                    mockProduct.setName("Mock Product " + productId);
                    mockProduct.setPrice(99.99);
                    mockProduct.setSku("MOCK-" + productId);
                    return mockProduct;
                }
                throw new ProductException("Failed to fetch product from inventory: " + e.getMessage());
            }
        } catch (Exception e) {
            if (!(e instanceof ProductException)) {
                System.err.println("Error fetching product: " + e.getMessage());
                throw new ProductException("Failed to fetch product: " + e.getMessage());
            }
            throw e;
        }
    }
    
    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }
    
    /**
     * Extracts the JWT token from the current request context.
     * 
     * @return The JWT token without the "Bearer " prefix, or null if not found
     */
    private String extractJwtTokenFromRequest() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    return authHeader.substring(7); // Remove "Bearer " prefix
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting JWT token from request: " + e.getMessage());
        }
        return null;
    }
}
