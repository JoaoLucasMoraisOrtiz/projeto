package com.example.msresident.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String user;
    private String password;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
