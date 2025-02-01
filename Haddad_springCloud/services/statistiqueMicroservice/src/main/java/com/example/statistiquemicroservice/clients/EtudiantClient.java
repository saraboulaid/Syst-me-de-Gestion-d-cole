package com.example.statistiquemicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "etudiant")
public interface EtudiantClient {
    @GetMapping("/api/etudiants/count")
    long count();

    @GetMapping("/api/etudiants/countInscriptions")
    long countInscriptions();

    @GetMapping("api/etudiants/inscriptions-top-modules")
    List<Map<String, Object>> getTopModules();

    @GetMapping("api/etudiants/inscriptions-top-etudiants")
    List<Map<String, Object>> getTopEtudiants();

}
