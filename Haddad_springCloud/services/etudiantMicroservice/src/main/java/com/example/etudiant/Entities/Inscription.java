package com.example.etudiant.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inscription")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_matricule", referencedColumnName = "matricule", nullable = false)
    @JsonBackReference
    private Etudiant etudiant;

    @Column(name = "module_id", nullable = false)
    private String moduleId;

    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;

}