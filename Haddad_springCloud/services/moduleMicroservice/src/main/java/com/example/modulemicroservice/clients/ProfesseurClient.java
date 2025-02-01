package com.example.modulemicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "professeurs-microservice")
public interface ProfesseurClient {

    @GetMapping("/api/professeurs/{pK}/exist")
    boolean exist(@PathVariable("pK") String pK);

}
