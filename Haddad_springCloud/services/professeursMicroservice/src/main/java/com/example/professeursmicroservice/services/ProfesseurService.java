package com.example.professeursmicroservice.services;

import com.example.professeursmicroservice.models.Professeur;

import java.util.List;
import java.util.Optional;

public interface ProfesseurService {
    Professeur save(Professeur professeur);
    Optional<Professeur> findByPk(String pk);
    List<Professeur> list();
    void delete(Professeur professeur);
}
