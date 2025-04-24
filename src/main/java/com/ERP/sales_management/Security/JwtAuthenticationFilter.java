package com.ERP.sales_management.Security;


import com.ERP.sales_management.config.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            logger.warn("Missing or invalid token in request header");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"statusCode\": 401, \"statusMessage\": \"Token is missing or invalid\", \"data\": null}");
            return;
        }

        token = token.substring(7); // Remove 'Bearer ' prefix

        if (!tokenValidator.isTokenValid(token)) {
            logger.warn("Invalid or expired token: " + token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"statusCode\": 401, \"statusMessage\": \"Invalid or expired token\", \"data\": null}");
            return;
        }

        filterChain.doFilter(request, response); // Proceed if valid
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Allow token-less access to these endpoints
        String path = request.getRequestURI();
        return path.startsWith("/user_management/api/auth");  // These endpoints can be accessed without a token
    }
}
