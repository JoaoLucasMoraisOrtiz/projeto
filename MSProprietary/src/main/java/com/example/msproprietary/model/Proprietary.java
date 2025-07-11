package com.example.msproprietary.model;

import jakarta.persistence.*;

@Entity
public class Proprietary extends Person {
    
    private boolean resident; // Indicates if the proprietary also lives in the property

    @ElementCollection
    @CollectionTable(name = "proprietary_units", joinColumns = @JoinColumn(name = "proprietary_id"))
    @Column(name = "unit_id")
    private java.util.List<Long> unitIds; // List of unit IDs owned by this proprietary

    // Getters and Setters
    public boolean isResident() { return resident; }
    public void setResident(boolean resident) { this.resident = resident; }
    
    public java.util.List<Long> getUnitIds() { return unitIds; }
    public void setUnitIds(java.util.List<Long> unitIds) { this.unitIds = unitIds; }
}
