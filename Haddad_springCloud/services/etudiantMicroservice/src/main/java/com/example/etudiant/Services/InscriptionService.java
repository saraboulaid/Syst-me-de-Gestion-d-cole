package com.example.etudiant.Services;

import com.example.etudiant.Entities.Inscription;

import java.util.List;
import java.util.Optional;

public interface InscriptionService {
    Inscription save(Inscription inscription);
    Optional<Inscription> findByPk(int pk);
    List<Inscription> list();
    void delete(Inscription inscription);
    long count();
    Optional<Inscription> findByEtudiantAndModule(String matricule, String moduleId);
    List<Inscription> findByEtudiant(String matricule);
}
