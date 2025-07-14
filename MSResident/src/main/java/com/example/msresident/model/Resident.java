package com.example.msresident.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resident extends Person {
    
    @Enumerated(EnumType.STRING)
    private ResidentType residentType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Unit unit;

    // Getters and Setters
    public ResidentType getResidentType() { return residentType; }
    public void setResidentType(ResidentType residentType) { this.residentType = residentType; }
    
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}
