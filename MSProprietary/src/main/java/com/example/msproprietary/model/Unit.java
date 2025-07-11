package com.example.msproprietary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;
    
    private String location;
    private double sizeSM;
    private Long condominiumId; // Reference to condominium in MSFinancialManagement

    // Getters and Setters
    public Long getUnitId() { return unitId; }
    public void setUnitId(Long unitId) { this.unitId = unitId; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public double getSizeSM() { return sizeSM; }
    public void setSizeSM(double sizeSM) { this.sizeSM = sizeSM; }
    
    public Long getCondominiumId() { return condominiumId; }
    public void setCondominiumId(Long condominiumId) { this.condominiumId = condominiumId; }
}
