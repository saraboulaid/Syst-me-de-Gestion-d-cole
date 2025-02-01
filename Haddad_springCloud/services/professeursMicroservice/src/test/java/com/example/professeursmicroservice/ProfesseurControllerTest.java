package com.example.professeursmicroservice;

import com.example.professeursmicroservice.controllers.ProfesseurController;
import com.example.professeursmicroservice.models.Professeur;
import com.example.professeursmicroservice.services.ProfesseurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class ProfesseurControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfesseurService professeurService;

    @InjectMocks
    private ProfesseurController professeurController;

    private Professeur professeur;

    @BeforeEach
    void setUp() {
        professeur = new Professeur();
        professeur.setNom("Prof1");
        professeur.setPrenom("Prof1");
        professeur.setMatricule("Prof1");
        mockMvc = MockMvcBuilders.standaloneSetup(professeurController).build();
    }

    @Test
    void testListProfesseurs() throws Exception {
        when(professeurService.list()).thenReturn(Arrays.asList(professeur));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/professeurs/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Prof1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenom").value("Prof1"));

        verify(professeurService, times(1)).list();
    }

    @Test
    void testCreateProfesseur() throws Exception {
        when(professeurService.save(any(Professeur.class))).thenReturn(professeur);

        mockMvc.perform(post("/api/professeurs/")
                        .contentType("application/json")
                        .content("{\"matricule\": \"Prof1\", \"nom\": \"Prof1\", \"prenom\": \"Prof1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Prof1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("Prof1"));

        verify(professeurService, times(1)).save(any(Professeur.class));
    }

    @Test
    void testUpdateProfesseur() throws Exception {
        when(professeurService.findByPk("Prof1")).thenReturn(Optional.of(professeur));
        when(professeurService.save(any(Professeur.class))).thenReturn(professeur);

        mockMvc.perform(put("/api/professeurs/Prof1/")
                        .contentType("application/json")
                        .content("{\"matricule\": \"Prof1\", \"nom\": \"Ahmad\", \"prenom\": \"Ahmad\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Ahmad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("Ahmad"));

        verify(professeurService, times(1)).findByPk("Prof1");
        verify(professeurService, times(1)).save(any(Professeur.class));
    }

    @Test
    void testUpdateProfesseurNotFound() throws Exception {
        when(professeurService.findByPk("Prof2")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/professeurs/Prof2/")
                        .contentType("application/json")
                        .content("{\"matricule\": \"Prof2\", \"nom\": \"Prof2\", \"prenom\": \"Prof2\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(professeurService, times(1)).findByPk("Prof2");
    }

    @Test
    void testDeleteProfesseur() throws Exception {
        when(professeurService.findByPk("Prof1")).thenReturn(Optional.of(professeur));

        mockMvc.perform(delete("/api/professeurs/Prof1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        verify(professeurService, times(1)).findByPk("Prof1");
        verify(professeurService, times(1)).delete(professeur);
    }

    @Test
    void testDeleteProfesseurNotFound() throws Exception {
        when(professeurService.findByPk("Prof2")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/professeurs/Prof2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(professeurService, times(1)).findByPk("Prof2");
    }

    @Test
    void testFindProfesseur() throws Exception {
        when(professeurService.findByPk("Prof1")).thenReturn(Optional.of(professeur));

        mockMvc.perform(get("/api/professeurs/Prof1/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Prof1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("Prof1"));

        verify(professeurService, times(1)).findByPk("Prof1");
    }

    @Test
    void testFindProfesseurNotFound() throws Exception {
        // Arrange
        when(professeurService.findByPk("Prof2")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/professeurs/Prof2/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(professeurService, times(1)).findByPk("Prof2");
    }

    @Test
    void testExistProfesseur() throws Exception {
        when(professeurService.findByPk("Prof1")).thenReturn(Optional.of(professeur));

        mockMvc.perform(get("/api/professeurs/Prof1/exist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        verify(professeurService, times(1)).findByPk("Prof1");
    }

    @Test
    void testExistProfesseurNotFound() throws Exception {
        when(professeurService.findByPk("Prof2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/professeurs/Prof2/exist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        verify(professeurService, times(1)).findByPk("Prof2");
    }

}
