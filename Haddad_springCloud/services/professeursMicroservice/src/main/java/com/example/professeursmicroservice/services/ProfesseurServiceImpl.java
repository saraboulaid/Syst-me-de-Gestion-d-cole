package com.example.professeursmicroservice.services;

import com.example.professeursmicroservice.models.Professeur;
import com.example.professeursmicroservice.repositories.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurServiceImpl implements ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;
    @Override
    public Professeur save(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    @Override
    public Optional<Professeur> findByPk(String pk) {
        return professeurRepository.findById(pk);
    }

    @Override
    public List<Professeur> list() {
        return professeurRepository.findAll();
    }

    @Override
    public void delete(Professeur professeur) {
        professeurRepository.delete(professeur);
    }

    @Override
    public long count(){return professeurRepository.count(); }
}
