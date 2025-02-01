package com.example.cloudgateway;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthClient authClient;

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String token = extractTokenFromRequest(request);

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        authClient.validateToken(token)
                .flatMap(isValid -> {
                    if (isValid) {
                        try {
                            filterChain.doFilter(request, response);
                        } catch (ServletException | IOException e) {
                            e.printStackTrace();
                        }
                        return Mono.empty();
                    } else {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        return Mono.empty();
                    }
                })
                .subscribe();
    }
}
