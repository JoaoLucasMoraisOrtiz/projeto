package com.example.msresident.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "MSResident");
        status.put("timestamp", LocalDateTime.now());
        status.put("version", "1.0.0");
        return ResponseEntity.ok(status);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Microserviço de Residentes");
        info.put("description", "Gerencia informações de residentes de condomínios");
        info.put("version", "1.0.0");
        info.put("port", 8081);
        return ResponseEntity.ok(info);
    }
}
