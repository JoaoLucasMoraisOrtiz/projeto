package com.example.msresident.controller;

import com.example.msresident.model.Resident;
import com.example.msresident.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    private ResidentRepository residentRepository;

    @GetMapping
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Optional<Resident> resident = residentRepository.findById(id);
        return resident.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Resident createResident(@RequestBody Resident resident) {
        return residentRepository.save(resident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable Long id, @RequestBody Resident resident) {
        if (!residentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        resident.setId(id);
        return ResponseEntity.ok(residentRepository.save(resident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        if (!residentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        residentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
