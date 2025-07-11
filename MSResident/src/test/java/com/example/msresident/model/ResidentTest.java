package com.example.msresident.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {

    private Resident resident;
    private Unit unit;

    @BeforeEach
    void setUp() {
        resident = new Resident();
        unit = new Unit();
    }

    @Test
    void shouldCreateResidentWithType() {
        // Given
        ResidentType type = ResidentType.OWNER;

        // When
        resident.setResidentType(type);

        // Then
        assertEquals(ResidentType.OWNER, resident.getResidentType());
    }

    @Test
    void shouldSetAndGetUnit() {
        // Given
        unit.setLocation("Bloco A, Apto 101");
        unit.setSizeSM(75.5);

        // When
        resident.setUnit(unit);

        // Then
        assertNotNull(resident.getUnit());
        assertEquals("Bloco A, Apto 101", resident.getUnit().getLocation());
        assertEquals(75.5, resident.getUnit().getSizeSM());
    }

    @Test
    void shouldInheritFromPerson() {
        // Given
        String name = "Maria Santos";

        // When
        resident.setName(name);

        // Then
        assertEquals(name, resident.getName());
        assertTrue(resident instanceof Person);
    }

    @Test
    void shouldTestAllResidentTypes() {
        // Test OWNER
        resident.setResidentType(ResidentType.OWNER);
        assertEquals(ResidentType.OWNER, resident.getResidentType());

        // Test TENANT
        resident.setResidentType(ResidentType.TENANT);
        assertEquals(ResidentType.TENANT, resident.getResidentType());

        // Test OTHER
        resident.setResidentType(ResidentType.OTHER);
        assertEquals(ResidentType.OTHER, resident.getResidentType());
    }
}
