package com.example.msproprietary.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "access_id")
    private Access access;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Address> addresses;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
    
    public Access getAccess() { return access; }
    public void setAccess(Access access) { this.access = access; }
    
    public java.util.List<Address> getAddresses() { return addresses; }
    public void setAddresses(java.util.List<Address> addresses) { this.addresses = addresses; }
}
