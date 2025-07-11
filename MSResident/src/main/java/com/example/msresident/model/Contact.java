package com.example.msresident.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String phoneHome;
    private String phoneCommercial;
    private String cellPhone;
    private String email;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPhoneHome() { return phoneHome; }
    public void setPhoneHome(String phoneHome) { this.phoneHome = phoneHome; }
    
    public String getPhoneCommercial() { return phoneCommercial; }
    public void setPhoneCommercial(String phoneCommercial) { this.phoneCommercial = phoneCommercial; }
    
    public String getCellPhone() { return cellPhone; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
