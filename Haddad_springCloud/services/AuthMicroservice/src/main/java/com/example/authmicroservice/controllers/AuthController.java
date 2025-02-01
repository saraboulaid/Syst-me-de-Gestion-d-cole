package com.example.authmicroservice.controllers;

import com.example.authmicroservice.models.User;
import com.example.authmicroservice.repositories.UserRepository;
import com.example.authmicroservice.requests.LoginRequest;
import com.example.authmicroservice.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PreAuthorize("hasAuthority('Administrateur')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "L'email existe déjà."));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Utilisateur ajouter avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Requête reçue pour : " + loginRequest.getEmail());
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Email ou mot de passe incorrect"));
        }

        String token = jwtUtil.generateToken(email, userOpt.get().getRole().name());
        return ResponseEntity.ok(Map.of("token", token, "role", userOpt.get().getRole()));
    }

    @PostMapping("/validate-token")
    public Mono<Boolean> validateToken(@RequestHeader("Authorization") String token){
        boolean isValid = jwtUtil.validateToken(token);
        if (isValid) {
            return Mono.just(isValid);
        } else {
            return Mono.just(false);
        }
    }
}
