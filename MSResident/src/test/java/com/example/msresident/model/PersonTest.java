package com.example.msresident.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;
    private Contact contact;
    private Access access;

    @BeforeEach
    void setUp() {
        person = new Person();
        contact = new Contact();
        access = new Access();
    }

    @Test
    void shouldCreatePersonWithValidData() {
        // Given
        String name = "João Silva";
        String cpf = "12345678901";
        Date birthDate = new Date();

        // When
        person.setName(name);
        person.setCpf(cpf);
        person.setBirthDate(birthDate);

        // Then
        assertEquals(name, person.getName());
        assertEquals(cpf, person.getCpf());
        assertEquals(birthDate, person.getBirthDate());
    }

    @Test
    void shouldSetAndGetContact() {
        // Given
        contact.setEmail("joao@email.com");
        contact.setCellPhone("11999999999");

        // When
        person.setContact(contact);

        // Then
        assertNotNull(person.getContact());
        assertEquals("joao@email.com", person.getContact().getEmail());
        assertEquals("11999999999", person.getContact().getCellPhone());
    }

    @Test
    void shouldSetAndGetAccess() {
        // Given
        access.setUser("joao.silva");
        access.setPassword("password123");

        // When
        person.setAccess(access);

        // Then
        assertNotNull(person.getAccess());
        assertEquals("joao.silva", person.getAccess().getUser());
        assertEquals("password123", person.getAccess().getPassword());
    }

    @Test
    void shouldInitializeWithNullValues() {
        // Given & When
        Person newPerson = new Person();

        // Then
        assertNull(newPerson.getName());
        assertNull(newPerson.getCpf());
        assertNull(newPerson.getBirthDate());
        assertNull(newPerson.getContact());
        assertNull(newPerson.getAccess());
    }
}
