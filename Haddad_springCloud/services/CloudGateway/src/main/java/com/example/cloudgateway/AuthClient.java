package com.example.cloudgateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

@Component
@FeignClient(name = "auth-microservice")
public interface AuthClient {

    @PostMapping("/auth/validate-token")
    Mono<Boolean> validateToken(@RequestHeader("Authorization") String token);
}

