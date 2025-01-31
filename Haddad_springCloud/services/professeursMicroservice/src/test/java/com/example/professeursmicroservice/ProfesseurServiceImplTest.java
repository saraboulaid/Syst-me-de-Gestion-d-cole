package com.example.professeursmicroservice;

import com.example.professeursmicroservice.models.Professeur;
import com.example.professeursmicroservice.repositories.ProfesseurRepository;
import com.example.professeursmicroservice.services.ProfesseurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfesseurServiceImplTest {

    @Mock
    private ProfesseurRepository professeurRepository;

    @InjectMocks
    private ProfesseurService professeurService;

    private Professeur mockProfesseur;

    @BeforeEach
    void setUp(){
        mockProfesseur = new Professeur("Prof1", "Professeur1", "Professeur");
    }

    @Test
    void testSave() {
        when(professeurService.save(mockProfesseur)).thenReturn(mockProfesseur);

        Professeur savedProfesseur = professeurService.save(mockProfesseur);

        assertNotNull(savedProfesseur);
        assertEquals("Prof1", savedProfesseur.getMatricule());
        assertEquals("Professeur1", savedProfesseur.getNom());
        assertEquals("Professeur", savedProfesseur.getPrenom());

        verify(professeurRepository, times(1)).save(mockProfesseur);
    }

    @Test
    void testFindByPk_Success() {
        when(professeurRepository.findById("Prof1")).thenReturn(Optional.of(mockProfesseur));

        Optional<Professeur> foundProfesseur = professeurService.findByPk("Prof1");

        assertTrue(foundProfesseur.isPresent());
        assertEquals("Prof1", foundProfesseur.get().getMatricule());
    }

    @Test
    void testFindByPk_NotFound() {
        when(professeurRepository.findById("Prof2")).thenReturn(Optional.empty());

        Optional<Professeur> foundProfesseur = professeurService.findByPk("Prof2");

        assertFalse(foundProfesseur.isPresent());
    }

    @Test
    void testList() {
        List<Professeur> professeurs = Arrays.asList(
                new Professeur("Prof1", "Professeur1", "Professeur"),
                new Professeur("Prof2", "Professeur2", "Professeur")
        );

        when(professeurRepository.findAll()).thenReturn(professeurs);

        List<Professeur> result = professeurService.list();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Prof1", result.get(0).getMatricule());
    }

    @Test
    void testDelete() {
        doNothing().when(professeurService).delete(mockProfesseur);

        professeurService.delete(mockProfesseur);

        verify(professeurRepository, times(1)).delete(mockProfesseur);
    }
}
