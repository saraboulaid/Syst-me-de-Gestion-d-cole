package com.example.etudiant.Services;

import com.example.etudiant.Entities.Etudiant;
import com.example.etudiant.Reposetories.EtudiantRepository;
import com.example.etudiant.clients.ModuleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImpl implements EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired ModuleClient moduleClient;

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

    @Override
    public long count(){
        return etudiantRepository.count();
    }

    @Override
    public boolean checkModuleExistence(String IdModule) {
        return moduleClient.exist(IdModule);
    }
}
