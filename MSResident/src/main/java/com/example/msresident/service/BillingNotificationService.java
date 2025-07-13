package com.example.msresident.service;

import com.example.msresident.model.Resident;
import com.example.msresident.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BillingNotificationService {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Executa todo dia 1 às 9h para enviar lembretes de pagamento
    @Scheduled(cron = "0 0 9 1 * ?")
    public void sendMonthlyPaymentReminders() {
        System.out.println("Iniciando envio de lembretes mensais de pagamento...");
        
        try {
            List<Resident> residents = residentRepository.findAll();
            
            for (Resident resident : residents) {
                try {
                    // Consulta faturas pendentes no MSFinancialManagement
                    String url = "http://localhost:8082/charges?residentId=" + resident.getId() + "&status=PENDING";
                    // Aqui você faria a chamada REST para buscar as faturas pendentes
                    // String pendingCharges = restTemplate.getForObject(url, String.class);
                    
                    // Enviar notificação (email, SMS, etc.)
                    sendNotificationToResident(resident);
                    
                } catch (Exception e) {
                    System.err.println("Erro ao enviar lembrete para o residente " + resident.getId() + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar residentes: " + e.getMessage());
        }
        
        System.out.println("Envio de lembretes mensais concluído.");
    }

    private void sendNotificationToResident(Resident resident) {
        // Implementar lógica de envio de notificação
        // Pode ser email, SMS, push notification, etc.
        System.out.println("Enviando lembrete de pagamento para: " + resident.getName());
    }
}
