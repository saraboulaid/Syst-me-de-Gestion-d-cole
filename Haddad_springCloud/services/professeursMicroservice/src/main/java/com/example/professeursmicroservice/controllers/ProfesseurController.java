package com.example.professeursmicroservice.controllers;

import com.example.professeursmicroservice.models.Professeur;
import com.example.professeursmicroservice.services.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8084" })
@RestController
@RequestMapping(value = "api/professeurs")
public class ProfesseurController {
    @Autowired
    ProfesseurService professeurService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Professeur>> list(){
        return ResponseEntity.ok(professeurService.list());
    }

    @PostMapping("/")
    public ResponseEntity<Professeur> create(@RequestBody Professeur professeur){
        professeur = professeurService.save(professeur);
        return ResponseEntity.status(HttpStatus.CREATED).body(professeur);
    }

    @PutMapping(value = "/{pK}/")
    public ResponseEntity<Professeur> update(@PathVariable final String pK, @RequestBody final Professeur professeur){
        final Optional<Professeur> maybeProfesseur = professeurService.findByPk(pK);

        if(maybeProfesseur.isEmpty())
            return ResponseEntity.notFound().build();

        Professeur updatedProfesseur = maybeProfesseur.get();
        updatedProfesseur.setNom(professeur.getNom());
        updatedProfesseur.setPrenom(professeur.getPrenom());

        updatedProfesseur = professeurService.save(updatedProfesseur);
        return ResponseEntity.accepted().body(updatedProfesseur);
    }

    @DeleteMapping(value = "/{pK}")
    public ResponseEntity<Object> delete (@PathVariable final String pK){
        final Optional<Professeur> maybeProfesseur = professeurService.findByPk(pK);

        if(maybeProfesseur.isEmpty())
            return ResponseEntity.notFound().build();

        professeurService.delete(maybeProfesseur.get());
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/{pK}/")
    public ResponseEntity<Professeur> find( @PathVariable final String pK){
        final Optional<Professeur> maybeProfesseur = professeurService.findByPk(pK);

        return maybeProfesseur.map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{pK}/exist")
    public ResponseEntity<Boolean> exist( @PathVariable final String pK){
        final Optional<Professeur> maybeProfesseur = professeurService.findByPk(pK);

         if(maybeProfesseur.isEmpty())
            return ResponseEntity.ok(false);

         return ResponseEntity.ok(true);

    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(professeurService.count());
    }
}
