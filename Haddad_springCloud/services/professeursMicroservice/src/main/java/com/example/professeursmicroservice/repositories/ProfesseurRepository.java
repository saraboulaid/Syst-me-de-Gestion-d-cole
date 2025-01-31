package com.example.professeursmicroservice.repositories;

import com.example.professeursmicroservice.models.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur, String> {
}
