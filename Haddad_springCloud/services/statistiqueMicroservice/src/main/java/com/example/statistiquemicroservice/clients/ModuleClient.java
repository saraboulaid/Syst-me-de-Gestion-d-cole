package com.example.statistiquemicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "module-microservice")
public interface ModuleClient {
    @GetMapping("/api/modules/count")
    long count();

    @GetMapping("/api/modules/{pk}")
    Map<String, Object> getModuleByPk(String pk);
}
