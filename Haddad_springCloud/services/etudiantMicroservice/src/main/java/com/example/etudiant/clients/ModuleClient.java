package com.example.etudiant.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "module-microservice")
public interface ModuleClient {

    @GetMapping("/api/modules/{pK}/exist")
    boolean exist(@PathVariable("pK") String pK);

}
