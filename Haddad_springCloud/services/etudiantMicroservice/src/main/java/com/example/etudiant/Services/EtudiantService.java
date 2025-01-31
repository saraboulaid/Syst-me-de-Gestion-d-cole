package com.example.etudiant.Services;

import com.example.etudiant.Entities.Etudiant;

import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    Etudiant save(Etudiant etudiant);
    Optional<Etudiant> findByPk(String pk);
    List<Etudiant> list();
    void delete(Etudiant etudiant);
}
