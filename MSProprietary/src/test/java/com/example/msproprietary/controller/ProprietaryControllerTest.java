package com.example.msproprietary.controller;

import com.example.msproprietary.model.Proprietary;
import com.example.msproprietary.repository.ProprietaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProprietaryController.class)
class ProprietaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProprietaryRepository proprietaryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Proprietary proprietary;

    @BeforeEach
    void setUp() {
        proprietary = new Proprietary();
        proprietary.setId(1L);
        proprietary.setName("Maria Silva");
    }

    @Test
    void shouldGetAllProprietaries() throws Exception {
        // Given
        when(proprietaryRepository.findAll()).thenReturn(Arrays.asList(proprietary));

        // When & Then
        mockMvc.perform(get("/proprietaries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria Silva"));

        verify(proprietaryRepository, times(1)).findAll();
    }

    @Test
    void shouldGetProprietaryById() throws Exception {
        // Given
        when(proprietaryRepository.findById(1L)).thenReturn(Optional.of(proprietary));

        // When & Then
        mockMvc.perform(get("/proprietaries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Silva"));

        verify(proprietaryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenProprietaryDoesNotExist() throws Exception {
        // Given
        when(proprietaryRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/proprietaries/999"))
                .andExpect(status().isNotFound());

        verify(proprietaryRepository, times(1)).findById(999L);
    }

    @Test
    void shouldCreateProprietary() throws Exception {
        // Given
        when(proprietaryRepository.save(any(Proprietary.class))).thenReturn(proprietary);

        // When & Then
        mockMvc.perform(post("/proprietaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proprietary)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Silva"));

        verify(proprietaryRepository, times(1)).save(any(Proprietary.class));
    }

    @Test
    void shouldUpdateProprietary() throws Exception {
        // Given
        when(proprietaryRepository.existsById(1L)).thenReturn(true);
        when(proprietaryRepository.save(any(Proprietary.class))).thenReturn(proprietary);

        // When & Then
        mockMvc.perform(put("/proprietaries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proprietary)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Silva"));

        verify(proprietaryRepository, times(1)).existsById(1L);
        verify(proprietaryRepository, times(1)).save(any(Proprietary.class));
    }

    @Test
    void shouldDeleteProprietary() throws Exception {
        // Given
        when(proprietaryRepository.existsById(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/proprietaries/1"))
                .andExpect(status().isNoContent());

        verify(proprietaryRepository, times(1)).existsById(1L);
        verify(proprietaryRepository, times(1)).deleteById(1L);
    }
}
