package com.example.msproprietary.controller;

import com.example.msproprietary.model.Proprietary;
import com.example.msproprietary.service.AsyncFinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/proprietaries")
public class ProprietaryController {

    @Autowired
    private AsyncFinancialReportService asyncFinancialReportService;

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
