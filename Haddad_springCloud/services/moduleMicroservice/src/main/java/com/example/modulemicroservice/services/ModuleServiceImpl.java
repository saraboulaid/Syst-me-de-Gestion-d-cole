package com.example.modulemicroservice.services;

import com.example.modulemicroservice.clients.ProfesseurClient;
import com.example.modulemicroservice.models.Module;
import com.example.modulemicroservice.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService{

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ProfesseurClient professeurClient;

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> findByPk(String pk) {
        return moduleRepository.findById(pk);
    }

    @Override
    public List<Module> list() {
        return moduleRepository.findAll();
    }

    @Override
    public void delete(Module module) {
        moduleRepository.delete(module);
    }

    @Override
    public long count(){
        return moduleRepository.count();
    }

    @Override
    public boolean checkProfesseurExistence(String IdProf) {
        return professeurClient.exist(IdProf);
    }

}
