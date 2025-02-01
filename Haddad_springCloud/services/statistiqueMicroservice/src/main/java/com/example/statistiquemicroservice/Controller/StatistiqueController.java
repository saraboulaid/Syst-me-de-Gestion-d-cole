package com.example.statistiquemicroservice.Controller;

import com.example.statistiquemicroservice.clients.EtudiantClient;
import com.example.statistiquemicroservice.clients.ModuleClient;
import com.example.statistiquemicroservice.clients.ProfesseurClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {
    @Autowired
    private ModuleClient moduleClient;
    @Autowired
    private EtudiantClient etudiantClient;
    @Autowired
    private ProfesseurClient professeurClient;

    @GetMapping("/global-stats")
    public ResponseEntity<Map<String, Long>> getGlobalStats() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("totalEtudiants", etudiantClient.count());
        stats.put("totalProfesseurs", professeurClient.count());
        stats.put("totalModules", moduleClient.count());
        stats.put("totalInscriptions", etudiantClient.countInscriptions());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/modules-populaires")
    public ResponseEntity<List<Map<String, Object>>> getModulesPopulaires() {
        List<Map<String, Object>> topModules = etudiantClient.getTopModules();
        return ResponseEntity.ok(topModules);
    }

    @GetMapping("/etudiants-actifs")
    public ResponseEntity<List<Map<String, Object>>> getEtudiantsActifs() {
        List<Map<String, Object>> topEtudiants = etudiantClient.getTopEtudiants();
        return ResponseEntity.ok(topEtudiants);
    }
}
