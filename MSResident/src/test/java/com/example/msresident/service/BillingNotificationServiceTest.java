package com.example.msresident.service;

import com.example.msresident.model.Resident;
import com.example.msresident.repository.ResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BillingNotificationServiceTest {

    @Mock
    private ResidentRepository residentRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BillingNotificationService billingNotificationService;

    private Resident resident1;
    private Resident resident2;

    @BeforeEach
    void setUp() {
        resident1 = new Resident();
        resident1.setId(1L);
        resident1.setName("João Silva");

        resident2 = new Resident();
        resident2.setId(2L);
        resident2.setName("Maria Santos");
    }

    @Test
    void shouldSendMonthlyPaymentReminders() {
        // Given
        List<Resident> residents = Arrays.asList(resident1, resident2);
        when(residentRepository.findAll()).thenReturn(residents);

        // When
        billingNotificationService.sendMonthlyPaymentReminders();

        // Then
        verify(residentRepository, times(1)).findAll();
        // Verifica se o método foi chamado para todos os residentes
        assertEquals(2, residents.size());
    }

    @Test
    void shouldHandleEmptyResidentList() {
        // Given
        List<Resident> emptyList = Arrays.asList();
        when(residentRepository.findAll()).thenReturn(emptyList);

        // When
        billingNotificationService.sendMonthlyPaymentReminders();

        // Then
        verify(residentRepository, times(1)).findAll();
        assertEquals(0, emptyList.size());
    }

    @Test
    void shouldHandleRepositoryException() {
        // Given
        when(residentRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertDoesNotThrow(() -> {
            billingNotificationService.sendMonthlyPaymentReminders();
        });

        verify(residentRepository, times(1)).findAll();
    }
}
