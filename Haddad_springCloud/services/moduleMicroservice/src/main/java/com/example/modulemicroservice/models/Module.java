package com.example.modulemicroservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Module {
    @Id
    private String IdModule;
    private String nom;
    private int nbrHeures;
    private String description;

}
