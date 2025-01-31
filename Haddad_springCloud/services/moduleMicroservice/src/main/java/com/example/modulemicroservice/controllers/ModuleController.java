package com.example.modulemicroservice.controllers;

import com.example.modulemicroservice.models.Module;
import com.example.modulemicroservice.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/modules")
public class ModuleController {
    @Autowired
    ModuleService moduleService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Module>> list(){
        return ResponseEntity.ok(moduleService.list());
    }

    @PostMapping("/")
    public ResponseEntity<Module> create(@RequestBody Module module){
        module = moduleService.save(module);
        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    @PutMapping(value = "/{pK}/")
    public ResponseEntity<Module> update(@PathVariable final String pK, @RequestBody final Module module){
        final Optional<Module> maybeModule = moduleService.findByPk(pK);

        if(maybeModule.isEmpty())
            return ResponseEntity.notFound().build();

        Module updatedModule = maybeModule.get();
        updatedModule.setNom(module.getNom());
        updatedModule.setDescription(module.getDescription());
        updatedModule.setNbrHeures(module.getNbrHeures());

        updatedModule = moduleService.save(updatedModule);
        return ResponseEntity.accepted().body(updatedModule);
    }

    @DeleteMapping(value = "/{pK}")
    public ResponseEntity<Object> delete (@PathVariable final String pK){
        final Optional<Module> maybeModule = moduleService.findByPk(pK);

        if(maybeModule.isEmpty())
            return ResponseEntity.notFound().build();

        moduleService.delete(maybeModule.get());
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/{pK}/")
    public ResponseEntity<Module> find( @PathVariable final String pK){
        final Optional<Module> maybeModule = moduleService.findByPk(pK);

        return maybeModule.map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

