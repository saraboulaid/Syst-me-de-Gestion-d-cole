package com.example.professeursmicroservice.repositories;

import com.example.professeursmicroservice.models.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfesseurRepository extends JpaRepository<Professeur, String> {
    @Query("SELECT COUNT(p) FROM Professeur p")
    long count();
}
