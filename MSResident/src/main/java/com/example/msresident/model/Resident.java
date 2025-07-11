package com.example.msresident.model;

import jakarta.persistence.*;

@Entity
public class Resident extends Person {
    
    @Enumerated(EnumType.STRING)
    private ResidentType residentType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    // Getters and Setters
    public ResidentType getResidentType() { return residentType; }
    public void setResidentType(ResidentType residentType) { this.residentType = residentType; }
    
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}
