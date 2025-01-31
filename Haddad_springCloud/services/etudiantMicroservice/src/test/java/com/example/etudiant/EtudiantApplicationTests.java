package com.example.etudiant;

import com.example.etudiant.Entities.Etudiant;
import com.example.etudiant.Entities.Inscription;
import com.example.etudiant.Services.EtudiantService;
import com.example.etudiant.Services.InscriptionService;
import com.example.etudiant.Controllers.EtudiantController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Active Mockito dans les tests pour pouvoir utiliser l'injection de dépendances et le mock des services
class EtudiantApplicationTests {

    private MockMvc mockMvc; //simule des appels HTTP sans démarrer un serveur

    @Mock
    private EtudiantService etudiantService; // Simule le service EtudiantService évitant toute connexion réelle à la base de données

    @Mock
    private InscriptionService inscriptionService; // Simule le service InscriptionService

    @InjectMocks
    private EtudiantController etudiantController; // Inject les mocks dans EtudiantController

    private ObjectMapper objectMapper = new ObjectMapper(); // Converit les obkets Java en JSON et vice versa
    private Etudiant etudiant; // Stocke une objet Etudiant fictif utilisé dans les tests

    @BeforeEach // Exécute avant chaque test, utiliser pour éviter la duplication de code
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantController).build(); // Permet d'exécuter des requêtes HTTP simulées contre EtudiantController sans serveur
        etudiant = new Etudiant("Et101", "Benattou", "Imane", new HashSet<>()); // Crée un étudiant fictif
    }

    /* Test GET /api/etudiants/ */
    @Test
    void testListEtudiants() throws Exception {
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantService.list()).thenReturn(etudiants); // Simule le comportement de etudiantService.list()

        mockMvc.perform(get("/api/etudiants/")) // Effectue une requête GET sur l'URL /api/etudiants/
                .andExpect(status().isOk()) // Vérifie que la réponse a un status HTTP 200 OK
                .andExpect(jsonPath("$.size()").value(1)) // Vérifie la taille de la liste retourner
                .andExpect(jsonPath("$[0].matricule").value("Et101")); // Vérifie le contenu de la liste
    }

    @Test
    void testFindByPk_WhenEtudiantExists() throws Exception {
        // Simule la présence d'un étudiant avec matricule "Et101"
        when(etudiantService.findByPk("Et101")).thenReturn(Optional.of(etudiant));

        // Envoie une requête GET et vérifie que l'étudiant est bien retourné avec un statut 200 OK
        mockMvc.perform(get("/api/etudiants/Et101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricule").value("Et101"));
    }

    @Test
    void testFindByPk_WhenEtudiantDoesNotExist() throws Exception {
        // Simule l'absence d'un étudiant avec matricule "Et102"
        when(etudiantService.findByPk("Et102")).thenReturn(Optional.empty());

        // Vérifie que la réponse est 404 NOT FOUND
        mockMvc.perform(get("/api/etudiants/Et102"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEtudiant() throws Exception {
        // Simule l'enregistrement d'un nouvel étudiant
        when(etudiantService.save(any(Etudiant.class))).thenReturn(etudiant);

        // Envoie une requête POST avec un étudiant en JSON et vérifie la réponse 201 CREATED
        mockMvc.perform(post("/api/etudiants/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.matricule").value("Et101"));
    }

    @Test
    void testUpdateEtudiant() throws Exception {
        // Étudiant mis à jour avec un prénom différent
        Etudiant updatedEtudiant = new Etudiant("Et101", "Benattou", "Ihsane", new HashSet<>());

        // Simule la recherche et l'enregistrement de la mise à jour
        when(etudiantService.findByPk("Et101")).thenReturn(Optional.of(etudiant));
        when(etudiantService.save(any(Etudiant.class))).thenReturn(updatedEtudiant);

        // Vérifie que la mise à jour est bien acceptée et que le prénom est bien "Ihsane"
        mockMvc.perform(put("/api/etudiants/Et101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEtudiant)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.prenom").value("Ihsane"));
    }

    @Test
    void testDeleteEtudiant() throws Exception {
        // Simule la présence d'un étudiant avant suppression
        when(etudiantService.findByPk("Et101")).thenReturn(Optional.of(etudiant));

        // Vérifie que la suppression retourne un statut 202 ACCEPTED
        mockMvc.perform(delete("/api/etudiants/Et101"))
                .andExpect(status().isAccepted());

        // Vérifie que la méthode delete() du service a bien été appelée
        Mockito.verify(etudiantService).delete(etudiant);
    }

    @Test
    void testAssignModule() throws Exception {
        // Simule une inscription à un module "M1"
        Inscription inscription = new Inscription(1, etudiant, "M1", LocalDateTime.now());

        // Simule la récupération de l'étudiant et l'enregistrement de l'inscription
        when(etudiantService.findByPk("Et101")).thenReturn(Optional.of(etudiant));
        when(inscriptionService.save(any(Inscription.class))).thenReturn(inscription);

        // Vérifie que l'inscription est bien créée avec un statut 201 CREATED
        mockMvc.perform(post("/api/etudiants/Et101/assign")
                        .param("moduleId", "M1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.moduleId").value("M1"));
    }

    @Test
    void testGetInscriptions() throws Exception {
        // Simule une liste d'inscriptions avec un seul module
        Inscription inscription = new Inscription(1, etudiant, "M1", LocalDateTime.now());
        List<Inscription> inscriptions = Collections.singletonList(inscription);

        // Simule la récupération de l'étudiant et de ses inscriptions
        when(etudiantService.findByPk("Et101")).thenReturn(Optional.of(etudiant));
        when(inscriptionService.findByEtudiant("Et101")).thenReturn(inscriptions);

        // Vérifie que la liste des inscriptions est bien retournée avec statut 200 OK
        mockMvc.perform(get("/api/etudiants/Et101/inscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].moduleId").value("M1"));
    }

    @Test
    void testDetachModule() throws Exception {
        // Simule la présence d'une inscription de l'étudiant au module "M1"
        Inscription inscription = new Inscription(1, etudiant, "M1", LocalDateTime.now());
        when(inscriptionService.findByEtudiantAndModule("Et101", "M1")).thenReturn(Optional.of(inscription));

        // Vérifie que la suppression de l'inscription retourne un statut 202 ACCEPTED
        mockMvc.perform(delete("/api/etudiants/Et101/detach")
                        .param("moduleId", "M1"))
                .andExpect(status().isAccepted());

        // Vérifie que la méthode delete() du service a bien été appelée
        Mockito.verify(inscriptionService).delete(inscription);
    }

    @Test
    void testDetachModule_WhenInscriptionDoesNotExist() throws Exception {
        // Simule l'absence d'inscription pour l'étudiant et le module "M1"
        when(inscriptionService.findByEtudiantAndModule("Et101", "M1")).thenReturn(Optional.empty());

        // Vérifie que la réponse est 404 NOT FOUND
        mockMvc.perform(delete("/api/etudiants/Et101/detach")
                        .param("moduleId", "M1"))
                .andExpect(status().isNotFound());
    }

}
