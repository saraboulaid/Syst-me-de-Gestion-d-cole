package com.example.statistiquemicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "professeurs-microservice")
public interface ProfesseurClient {
    @GetMapping("/api/professeurs/count")
    long count();

}
