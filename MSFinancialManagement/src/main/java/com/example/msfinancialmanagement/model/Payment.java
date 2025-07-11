package com.example.msfinancialmanagement.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    
    private double amountPaid;
    private String paymentMethod;
    private String proofOfPaymentUrl; // URL or path to the uploaded proof file

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_id")
    private Charge charge;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getProofOfPaymentUrl() { return proofOfPaymentUrl; }
    public void setProofOfPaymentUrl(String proofOfPaymentUrl) { this.proofOfPaymentUrl = proofOfPaymentUrl; }
    
    public Charge getCharge() { return charge; }
    public void setCharge(Charge charge) { this.charge = charge; }
}
