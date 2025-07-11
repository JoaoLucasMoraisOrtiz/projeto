package com.example.msfinancialmanagement.model;

import jakarta.persistence.*;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;
    
    private String location;
    private double sizeSM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Charge> charges;

    // Getters and Setters
    public Long getUnitId() { return unitId; }
    public void setUnitId(Long unitId) { this.unitId = unitId; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public double getSizeSM() { return sizeSM; }
    public void setSizeSM(double sizeSM) { this.sizeSM = sizeSM; }
    
    public Condominium getCondominium() { return condominium; }
    public void setCondominium(Condominium condominium) { this.condominium = condominium; }
    
    public java.util.List<Charge> getCharges() { return charges; }
    public void setCharges(java.util.List<Charge> charges) { this.charges = charges; }
}
