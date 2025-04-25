package com.ERP.sales_management.config;


import com.ERP.sales_management.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user_management/api/auth/**").permitAll()  // Authentication-related endpoints are publicly accessible
                        .requestMatchers("inventory_management/api/product/**").permitAll()
                        .requestMatchers("inventory_management/api/category/**").permitAll()
                        .requestMatchers("/sales_management/api/customer/**").permitAll()
                        .requestMatchers("/sales_management/api/orders/**").permitAll()
                        .requestMatchers("/sales_management/api/customer/**").permitAll()
                        .requestMatchers("/sales_management/api/invoices/**").permitAll()
                        .requestMatchers("/sales_management/api/order-items/**").permitAll()
                        .requestMatchers("/sales_management/api/payments/**").permitAll()
                        .requestMatchers("/sales-management/api/sales-orders/**").permitAll()
                        .requestMatchers("/sales-management/api/**").permitAll()

                        .anyRequest().authenticated()  // All other endpoints are secured
                )
                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Adding JWT filter
                .build();
    }
}
