package com.example.modulemicroservice.repositories;

import com.example.modulemicroservice.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModuleRepository extends JpaRepository<Module, String> {
    @Query("SELECT COUNT(m) FROM Module m")
    long count();
}
