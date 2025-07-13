package com.example.msproprietary.controller;

import com.example.msproprietary.model.Proprietary;
import com.example.msproprietary.repository.ProprietaryRepository;
import com.example.msproprietary.service.AsyncFinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/proprietaries")
public class ProprietaryController {

    @Autowired
    private ProprietaryRepository proprietaryRepository;

    @Autowired
    private AsyncFinancialReportService asyncFinancialReportService;

    @GetMapping
    public List<Proprietary> getAllProprietaries() {
        return proprietaryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietary> getProprietaryById(@PathVariable Long id) {
        Optional<Proprietary> proprietary = proprietaryRepository.findById(id);
        return proprietary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proprietary createProprietary(@RequestBody Proprietary proprietary) {
        return proprietaryRepository.save(proprietary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietary> updateProprietary(@PathVariable Long id, @RequestBody Proprietary proprietary) {
        if (!proprietaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proprietary.setId(id);
        return ResponseEntity.ok(proprietaryRepository.save(proprietary));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietary(@PathVariable Long id) {
        if (!proprietaryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proprietaryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/generate-financial-report")
    public ResponseEntity<String> generateFinancialReport(@PathVariable Long id, @RequestBody Proprietary proprietary) {
        try {
            // Inicia a geração assíncrona do relatório
            CompletableFuture<String> futureReport = asyncFinancialReportService.generateFinancialReport(proprietary);
            
            // Retorna imediatamente ao cliente
            return ResponseEntity.accepted().body("Relatório sendo gerado. Você será notificado quando estiver pronto.");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao iniciar geração do relatório: " + e.getMessage());
        }
    }

    @PostMapping("/send-bulk-notifications")
    public ResponseEntity<String> sendBulkNotifications() {
        try {
            // Inicia o envio assíncrono de notificações
            asyncFinancialReportService.sendBulkNotifications();
            
            return ResponseEntity.accepted().body("Envio de notificações em lote iniciado.");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao iniciar envio de notificações: " + e.getMessage());
        }
    }
}
