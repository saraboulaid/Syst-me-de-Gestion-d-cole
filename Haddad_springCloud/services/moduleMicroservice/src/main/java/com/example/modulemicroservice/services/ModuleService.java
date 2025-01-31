package com.example.modulemicroservice.services;

import com.example.modulemicroservice.models.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleService {
    Module save(Module module);
    Optional<Module> findByPk(String pk);
    List<Module> list();
    void delete(Module module);
}
