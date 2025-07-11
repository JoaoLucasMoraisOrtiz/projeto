package com.example.msresident.controller;

import com.example.msresident.model.Resident;
import com.example.msresident.repository.ResidentRepository;
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

@WebMvcTest(ResidentController.class)
class ResidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResidentRepository residentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Resident resident;

    @BeforeEach
    void setUp() {
        resident = new Resident();
        resident.setId(1L);
        resident.setName("João Silva");
    }

    @Test
    void shouldGetAllResidents() throws Exception {
        // Given
        when(residentRepository.findAll()).thenReturn(Arrays.asList(resident));

        // When & Then
        mockMvc.perform(get("/residents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("João Silva"));

        verify(residentRepository, times(1)).findAll();
    }

    @Test
    void shouldGetResidentById() throws Exception {
        // Given
        when(residentRepository.findById(1L)).thenReturn(Optional.of(resident));

        // When & Then
        mockMvc.perform(get("/residents/1"))
                .andExpected(status().isOk())
                .andExpected(jsonPath("$.name").value("João Silva"));

        verify(residentRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenResidentDoesNotExist() throws Exception {
        // Given
        when(residentRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/residents/999"))
                .andExpected(status().isNotFound());

        verify(residentRepository, times(1)).findById(999L);
    }

    @Test
    void shouldCreateResident() throws Exception {
        // Given
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        // When & Then
        mockMvc.perform(post("/residents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resident)))
                .andExpected(status().isOk())
                .andExpected(jsonPath("$.name").value("João Silva"));

        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void shouldUpdateResident() throws Exception {
        // Given
        when(residentRepository.existsById(1L)).thenReturn(true);
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        // When & Then
        mockMvc.perform(put("/residents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resident)))
                .andExpected(status().isOk())
                .andExpected(jsonPath("$.name").value("João Silva"));

        verify(residentRepository, times(1)).existsById(1L);
        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void shouldDeleteResident() throws Exception {
        // Given
        when(residentRepository.existsById(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/residents/1"))
                .andExpected(status().isNoContent());

        verify(residentRepository, times(1)).existsById(1L);
        verify(residentRepository, times(1)).deleteById(1L);
    }
}
