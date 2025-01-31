package com.example.modulemicroservice;

import com.example.modulemicroservice.models.Module;
import com.example.modulemicroservice.repositories.ModuleRepository;
import com.example.modulemicroservice.services.ModuleServiceImpl;
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
class ModuleServiceImplTest {

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    private Module mockModule;

    @BeforeEach
    void setUp() {
        mockModule = new Module();
        mockModule.setIdModule("MATH");
        mockModule.setNom("Mathématiques");
        mockModule.setDescription("module math");
        mockModule.setNbrHeures(20);
    }

    @Test
    void testSave() {
        when(moduleRepository.save(mockModule)).thenReturn(mockModule);

        Module savedModule = moduleService.save(mockModule);

        assertNotNull(savedModule);
        assertEquals("MATH", savedModule.getIdModule());
        assertEquals("Mathématiques", savedModule.getNom());
        assertEquals("module math", savedModule.getDescription());
        assertEquals(20, savedModule.getNbrHeures());


        verify(moduleRepository, times(1)).save(mockModule);
    }

    @Test
    void testFindByPk_Success() {
        when(moduleRepository.findById("MATH")).thenReturn(Optional.of(mockModule));

        Optional<Module> foundModule = moduleService.findByPk("MATH");

        assertTrue(foundModule.isPresent());
        assertEquals("MATH", foundModule.get().getIdModule());
    }

    @Test
    void testFindByPk_NotFound() {
        when(moduleRepository.findById("cl")).thenReturn(Optional.empty());

        Optional<Module> foundModule = moduleService.findByPk("cl");

        assertFalse(foundModule.isPresent());
    }

    @Test
    void testList() {
        List<Module> modules = Arrays.asList(
                new Module("MATH", "Mathématiques", 20, "module math"),
                new Module("PHY", "Physique", 30, "module physique")
        );

        when(moduleRepository.findAll()).thenReturn(modules);

        List<Module> result = moduleService.list();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("MATH", result.get(0).getIdModule());
    }

    @Test
    void testDelete() {
        doNothing().when(moduleRepository).delete(mockModule);

        moduleService.delete(mockModule);

        verify(moduleRepository, times(1)).delete(mockModule);
    }
}
