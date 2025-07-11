package com.example.trabFinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;

@Entity
@Table(name = "rent_invoice")
public class RentInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dueDate;
    private Double amount;
    private Boolean paid;
    private String paymentProofPath;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "proprietary_id")
    private Proprietary proprietary;

    public Long getId() { return id; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Boolean getPaid() { return paid; }
    public void setPaid(Boolean paid) { this.paid = paid; }
    public String getPaymentProofPath() { return paymentProofPath; }
    public void setPaymentProofPath(String paymentProofPath) { this.paymentProofPath = paymentProofPath; }
    public Resident getResident() { return resident; }
    public void setResident(Resident resident) { this.resident = resident; }
    public Proprietary getProprietary() { return proprietary; }
    public void setProprietary(Proprietary proprietary) { this.proprietary = proprietary; }
}
