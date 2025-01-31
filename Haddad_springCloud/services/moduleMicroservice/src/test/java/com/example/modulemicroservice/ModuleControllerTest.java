package com.example.modulemicroservice;

import com.example.modulemicroservice.controllers.ModuleController;
import com.example.modulemicroservice.models.Module;
import com.example.modulemicroservice.services.ModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ModuleControllerTest {

    @Mock
    private ModuleService moduleService;

    @InjectMocks
    private ModuleController moduleController;

    private MockMvc mockMvc;

    private Module mockModule;

    @BeforeEach
    void setUp() {
        mockModule = new Module("MATH", "Mathématiques", 40, "Module de maths");
        mockMvc = MockMvcBuilders.standaloneSetup(moduleController).build();
    }

    @Test
    void testListModules() throws Exception {
        List<Module> modules = Arrays.asList(mockModule);
        when(moduleService.list()).thenReturn(modules);

        mockMvc.perform(get("/api/modules/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idModule").value("MATH"));
    }

    @Test
    void testCreateModule() throws Exception {
        when(moduleService.save(any(Module.class))).thenReturn(mockModule);

        String moduleJson = """
                {
                  "idModule": "MATH",
                  "nom": "Mathématiques",
                  "nbrHeures": 40,
                  "description": "Module de maths"
                }
                """;

        mockMvc.perform(post("/api/modules/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moduleJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idModule").value("MATH"))
                .andExpect(jsonPath("$.nom").value("Mathématiques"));
    }

    @Test
    void testFindModuleById_Success() throws Exception {
        when(moduleService.findByPk("MATH")).thenReturn(Optional.of(mockModule));

        mockMvc.perform(get("/api/modules/MATH/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idModule").value("MATH"))
                .andExpect(jsonPath("$.nom").value("Mathématiques"));
    }

    @Test
    void testFindModuleById_NotFound() throws Exception {
        when(moduleService.findByPk("cl")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/modules/cl/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateModule() throws Exception {
        when(moduleService.findByPk("MATH")).thenReturn(Optional.of(mockModule));
        when(moduleService.save(any(Module.class))).thenReturn(mockModule);

        String updatedModuleJson = """
                {
                  "idModule": "MATH",
                  "nom": "Maths avancés",
                  "nbrHeures": 45,
                  "description": "Module avancé de maths"
                }
                """;

        mockMvc.perform(put("/api/modules/MATH/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedModuleJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.nom").value("Maths avancés"));
    }

    @Test
    void testDeleteModule_Success() throws Exception {
        when(moduleService.findByPk("MATH")).thenReturn(Optional.of(mockModule));
        doNothing().when(moduleService).delete(mockModule);

        mockMvc.perform(delete("/api/modules/MATH"))
                .andExpect(status().isAccepted());

        verify(moduleService, times(1)).delete(mockModule);
    }

    @Test
    void testDeleteModule_NotFound() throws Exception {
        when(moduleService.findByPk("cl")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/modules/cl"))
                .andExpect(status().isNotFound());
    }
}
