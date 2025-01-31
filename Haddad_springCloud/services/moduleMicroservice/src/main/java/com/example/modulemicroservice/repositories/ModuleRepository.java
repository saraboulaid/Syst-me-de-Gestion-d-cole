package com.example.modulemicroservice.repositories;

import com.example.modulemicroservice.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, String> {
}
