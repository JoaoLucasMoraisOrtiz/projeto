package com.example.msfinancialmanagement.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double value;
    
    @Temporal(TemporalType.DATE)
    private Date emissionDate;
    
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    private String status; // PENDING, PAID, OVERDUE
    
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;
    
    private Long residentId; // Reference to resident in MSResident

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToOne(mappedBy = "charge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    
    public Date getEmissionDate() { return emissionDate; }
    public void setEmissionDate(Date emissionDate) { this.emissionDate = emissionDate; }
    
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public ChargeType getChargeType() { return chargeType; }
    public void setChargeType(ChargeType chargeType) { this.chargeType = chargeType; }
    
    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }
    
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
