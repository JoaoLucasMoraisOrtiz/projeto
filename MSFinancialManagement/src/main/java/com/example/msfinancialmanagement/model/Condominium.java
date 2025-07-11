package com.example.msfinancialmanagement.model;

import jakarta.persistence.*;

@Entity
public class Condominium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private CondominiumType type;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "condominium", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Unit> units;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public CondominiumType getType() { return type; }
    public void setType(CondominiumType type) { this.type = type; }
    
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    
    public java.util.List<Unit> getUnits() { return units; }
    public void setUnits(java.util.List<Unit> units) { this.units = units; }
}
