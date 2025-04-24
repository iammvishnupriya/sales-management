package com.ERP.sales_management.config;

import com.ERP.sales_management.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidator.class);

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public TokenValidator(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public boolean isTokenValid(String token) {
        try {
            SuccessResponse<Boolean> response = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8080/user_management/api/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> Mono.error(new RuntimeException("Invalid Token")))
                    .bodyToMono(new ParameterizedTypeReference<SuccessResponse<Boolean>>() {})
                    .block();

            if (response != null && Boolean.TRUE.equals(response.getData())) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Error during token validation", e);
        }
        return false;
    }
}
