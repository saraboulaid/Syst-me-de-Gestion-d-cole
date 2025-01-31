package com.example.etudiant.Services;

import com.example.etudiant.Entities.Etudiant;
import com.example.etudiant.Reposetories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImpl implements EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Optional<Etudiant> findByPk(String pk) {
        return etudiantRepository.findById(pk);
    }

    @Override
    public List<Etudiant> list() {
        return etudiantRepository.findAll();
    }

    @Override
    public void delete(Etudiant etudiant) {
        etudiantRepository.delete(etudiant);
    }
}
