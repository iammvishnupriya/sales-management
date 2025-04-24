package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.UserResponseDto;
import com.ERP.sales_management.Service.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class UserServiceClientImpl implements UserServiceClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public UserResponseDto getUserById(int userId, String jwtToken) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-MANAGEMENT-SERVICE/api/users/" + userId)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .block(); // blocking call since this is not reactive service
    }

}
