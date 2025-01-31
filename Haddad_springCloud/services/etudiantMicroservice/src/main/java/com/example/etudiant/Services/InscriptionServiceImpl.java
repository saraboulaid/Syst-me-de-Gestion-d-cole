package com.example.etudiant.Services;

import com.example.etudiant.Entities.Inscription;
import com.example.etudiant.Reposetories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionServiceImpl implements InscriptionService{
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Override
    public Inscription save(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Optional<Inscription> findByPk(int pk) {
        return inscriptionRepository.findById(pk);
    }

    @Override
    public List<Inscription> list() {
        return inscriptionRepository.findAll();
    }

    @Override
    public void delete(Inscription inscription) {
        inscriptionRepository.delete(inscription);
    }

    @Override
    public Optional<Inscription> findByEtudiantAndModule(String matricule, String moduleId) {
        return inscriptionRepository.findByEtudiantMatriculeAndModuleId(matricule, moduleId);
    }

    @Override
    public List<Inscription> findByEtudiant(String matricule) {
        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantMatricule(matricule);
        return inscriptions;
    }
}
