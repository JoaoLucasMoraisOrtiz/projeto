package com.example.msfinancialmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ChargeTest {

    private Charge charge;
    private Unit unit;
    private Payment payment;

    @BeforeEach
    void setUp() {
        charge = new Charge();
        unit = new Unit();
        payment = new Payment();
    }

    @Test
    void shouldCreateChargeWithValidData() {
        // Given
        double value = 500.0;
        Date emissionDate = new Date();
        Date dueDate = new Date();
        String status = "PENDING";
        ChargeType chargeType = ChargeType.MONTHLY_QUOTA;
        Long residentId = 1L;

        // When
        charge.setValue(value);
        charge.setEmissionDate(emissionDate);
        charge.setDueDate(dueDate);
        charge.setStatus(status);
        charge.setChargeType(chargeType);
        charge.setResidentId(residentId);

        // Then
        assertEquals(value, charge.getValue());
        assertEquals(emissionDate, charge.getEmissionDate());
        assertEquals(dueDate, charge.getDueDate());
        assertEquals(status, charge.getStatus());
        assertEquals(chargeType, charge.getChargeType());
        assertEquals(residentId, charge.getResidentId());
    }

    @Test
    void shouldSetAndGetUnit() {
        // Given
        unit.setLocation("Bloco A, Apto 101");

        // When
        charge.setUnit(unit);

        // Then
        assertNotNull(charge.getUnit());
        assertEquals("Bloco A, Apto 101", charge.getUnit().getLocation());
    }

    @Test
    void shouldSetAndGetPayment() {
        // Given
        payment.setAmountPaid(500.0);
        payment.setPaymentMethod("PIX");

        // When
        charge.setPayment(payment);

        // Then
        assertNotNull(charge.getPayment());
        assertEquals(500.0, charge.getPayment().getAmountPaid());
        assertEquals("PIX", charge.getPayment().getPaymentMethod());
    }

    @Test
    void shouldTestChargeTypes() {
        // Test MONTHLY_QUOTA
        charge.setChargeType(ChargeType.MONTHLY_QUOTA);
        assertEquals(ChargeType.MONTHLY_QUOTA, charge.getChargeType());

        // Test FINE
        charge.setChargeType(ChargeType.FINE);
        assertEquals(ChargeType.FINE, charge.getChargeType());
    }

    @Test
    void shouldInitializeWithNullValues() {
        // Given & When
        Charge newCharge = new Charge();

        // Then
        assertEquals(0.0, newCharge.getValue());
        assertNull(newCharge.getEmissionDate());
        assertNull(newCharge.getDueDate());
        assertNull(newCharge.getStatus());
        assertNull(newCharge.getChargeType());
        assertNull(newCharge.getResidentId());
        assertNull(newCharge.getUnit());
        assertNull(newCharge.getPayment());
    }
}
