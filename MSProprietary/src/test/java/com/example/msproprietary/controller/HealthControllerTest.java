package com.example.mspr    @Test
    void shouldReturnHealthInfo() throws Exception {
        mockMvc.perform(get("/health/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("Microserviço de Proprietários"))
                .andExpected(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.description").value("Gerencia informações de proprietários de condomínios"))
                .andExpect(jsonPath("$.port").value(8083));
    }y.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("MSProprietary"));
    }

    @Test
    void shouldReturnHealthInfo() throws Exception {
        mockMvc.perform(get("/health/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("MSProprietary"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.description").value("Microservice for proprietary management"));
    }
}
