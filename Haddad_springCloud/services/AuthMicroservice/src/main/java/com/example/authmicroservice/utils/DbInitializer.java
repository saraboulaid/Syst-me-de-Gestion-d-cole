package com.example.authmicroservice.utils;

import com.example.authmicroservice.models.Role;
import com.example.authmicroservice.models.User;
import com.example.authmicroservice.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbInitializer {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin@123"));
            adminUser.setRole(Role.Administrateur);

            userRepository.save(adminUser);

            System.out.println("Administrateur ajouter avec succès.");
        }
    }
}
