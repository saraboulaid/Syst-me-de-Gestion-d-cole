package com.example.etudiant.Controllers;

import com.example.etudiant.Entities.Etudiant;
import com.example.etudiant.Entities.Inscription;
import com.example.etudiant.Reposetories.InscriptionRepository;
import com.example.etudiant.Services.EtudiantService;
import com.example.etudiant.Services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8084")
@RestController
@RequestMapping(value = "/api/etudiants")
public class EtudiantController {
    @Autowired
    EtudiantService etudiantService;
    @Autowired
    InscriptionService inscriptionService;
    @Autowired
    InscriptionRepository inscriptionRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<Etudiant>> list(){
        return ResponseEntity.ok(etudiantService.list());
    }

    @GetMapping(value = "/{pk}")
    public ResponseEntity<Etudiant> findByPk(@PathVariable final String pk){
        final Optional<Etudiant> maybeEtudiant = etudiantService.findByPk(pk);

        return maybeEtudiant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Etudiant> create(@RequestBody Etudiant etudiant){
        etudiant = etudiantService.save(etudiant);
        etudiant.setInscriptions(new HashSet<>());
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiant);
    }

    @PutMapping(value = "/{pk}")
    public ResponseEntity<Etudiant> update(@PathVariable final String pk, @RequestBody final Etudiant etudiant){
        final Optional<Etudiant> maybeEtudiant = etudiantService.findByPk(pk);
        if (maybeEtudiant.isEmpty())
            return ResponseEntity.notFound().build();
        Etudiant updatedEtudiant = maybeEtudiant.get();
        updatedEtudiant.setNom(etudiant.getNom());
        updatedEtudiant.setPrenom(etudiant.getPrenom());

        updatedEtudiant = etudiantService.save(updatedEtudiant);
        return ResponseEntity.accepted().body(updatedEtudiant);
    }

    @DeleteMapping(value = "/{pk}")
    public ResponseEntity<Object> delete(@PathVariable final String pk) {
        final Optional<Etudiant> maybeEtudiant = etudiantService.findByPk(pk);
        if (maybeEtudiant.isEmpty())
            return ResponseEntity.notFound().build();
        etudiantService.delete(maybeEtudiant.get());

        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(etudiantService.count());
    }

    // Gestion des inscriptions

    @PostMapping("/{pk}/assign")
    public ResponseEntity<Inscription> assignModule(@PathVariable String pk, @RequestParam String moduleId){
        Optional<Etudiant> maybeEtudiant = etudiantService.findByPk(pk);
        if (maybeEtudiant.isEmpty())
            return ResponseEntity.notFound().build();

        if (!etudiantService.checkModuleExistence(moduleId)){
            return ResponseEntity.notFound().build();
        }

        Etudiant etudiant = maybeEtudiant.get();

        for (Inscription i : etudiant.getInscriptions()) {
            if (i.getModuleId().equals(moduleId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        Inscription inscription = new Inscription();
        inscription.setEtudiant(maybeEtudiant.get());
        inscription.setModuleId(moduleId);
        inscription.setDateInscription(LocalDateTime.now());

        etudiant.getInscriptions().add(inscription);
        inscription = inscriptionService.save(inscription);

        return ResponseEntity.status(HttpStatus.CREATED).body(inscription);
    }

    @DeleteMapping("/{pk}/detach")
    public ResponseEntity<Void> detachModule(@PathVariable String pk, @RequestParam String moduleId){
        Optional<Inscription> maybeInscription = inscriptionService.findByEtudiantAndModule(pk, moduleId);
        if (maybeInscription.isEmpty())
            return ResponseEntity.notFound().build();

        inscriptionService.delete(maybeInscription.get());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{pk}/inscriptions")
    public ResponseEntity<List<Inscription>> getInscriptions(@PathVariable String pk){
        Optional<Etudiant> maybeEtudiant = etudiantService.findByPk(pk);
        if (maybeEtudiant.isEmpty())
            return ResponseEntity.notFound().build();

        List<Inscription> inscriptions = inscriptionService.findByEtudiant(pk);
        return ResponseEntity.ok(inscriptions);
    }

    @GetMapping(value = "/countInscriptions")
    public ResponseEntity<Long> countInscriptions(){
        return ResponseEntity.ok(inscriptionService.count());
    }

    // Statistiques

    @GetMapping("/inscriptions-top-modules")
    public ResponseEntity<List<Map<String, Object>>> getTopModules() {
        List<Object[]> results = inscriptionRepository.findTopModules();
        List<Map<String, Object>> topModules = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> moduleData = new HashMap<>();
            moduleData.put("moduleId", result[0]);
            moduleData.put("totalInscriptions", result[1]);
            topModules.add(moduleData);
        }

        return ResponseEntity.ok(topModules);
    }

    @GetMapping("/inscriptions-top-etudiants")
    public ResponseEntity<List<Map<String, Object>>> getTopEtudiants() {
        List<Object[]> results = inscriptionRepository.findTopEtudiants();
        List<Map<String, Object>> topEtudiants = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> etudiantData = new HashMap<>();
            etudiantData.put("matricule", result[0]);
            etudiantData.put("totalInscriptions", result[1]);

            topEtudiants.add(etudiantData);
        }

        return ResponseEntity.ok(topEtudiants);
    }
}
