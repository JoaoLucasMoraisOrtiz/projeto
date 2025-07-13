package com.example.msproprietary.service;

import com.example.msproprietary.model.Proprietary;
import com.example.msproprietary.repository.ProprietaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncFinancialReportService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    private ProprietaryRepository proprietaryRepository;

    @Async
    public CompletableFuture<String> generateFinancialReport(Proprietary proprietary) {
        try {
            System.out.println("Iniciando geração de relatório financeiro para proprietário: " + proprietary.getName());
            
            // Simula uma operação demorada
            Thread.sleep(5000);
            
            StringBuilder report = new StringBuilder();
            report.append("Relatório Financeiro para: ").append(proprietary.getName()).append("\n");
            report.append("======================================\n");
            
            // Para cada unidade do proprietário, buscar informações financeiras
            for (Long unitId : proprietary.getUnitIds()) {
                try {
                    // Simulação de chamada para MSFinancialManagement
                    String url = "http://localhost:8082/charges?unitId=" + unitId;
                    // String charges = restTemplate.getForObject(url, String.class);
                    
                    report.append("Unidade ID: ").append(unitId).append("\n");
                    report.append("- Total de cobranças: [dados do MSFinancialManagement]\n");
                    report.append("- Pagamentos recebidos: [dados do MSFinancialManagement]\n");
                    report.append("- Saldo pendente: [calculado]\n\n");
                    
                } catch (Exception e) {
                    report.append("Erro ao buscar dados da unidade ").append(unitId).append(": ").append(e.getMessage()).append("\n");
                }
            }
            
            System.out.println("Relatório financeiro gerado com sucesso para: " + proprietary.getName());
            
            // Aqui você poderia enviar o relatório por email, salvar em arquivo, etc.
            // emailService.sendReport(proprietary.getContact().getEmail(), report.toString());
            
            return CompletableFuture.completedFuture(report.toString());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Erro: Operação interrompida");
        }
    }
    
    @Async
    public CompletableFuture<Void> sendBulkNotifications() {
        try {
            System.out.println("Iniciando envio em lote de notificações...");
            
            List<Proprietary> proprietaries = proprietaryRepository.findAll();
            
            for (Proprietary proprietary : proprietaries) {
                // Simula envio de notificação
                sendNotificationToProprietary(proprietary);
                
                // Pequena pausa entre envios
                Thread.sleep(100);
            }
            
            System.out.println("Notificações em lote enviadas com sucesso! Total: " + proprietaries.size());
            
            return CompletableFuture.completedFuture(null);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            System.err.println("Erro no envio de notificações em lote: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }
    
    private void sendNotificationToProprietary(Proprietary proprietary) {
        // Implementar lógica de envio de notificação
        // Pode ser email, SMS, push notification, etc.
        String email = proprietary.getContact() != null ? proprietary.getContact().getEmail() : "N/A";
        System.out.println("Enviando notificação para: " + proprietary.getName() + " (" + email + ")");
    }
}
