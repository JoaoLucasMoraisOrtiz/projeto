package com.example.trabFinal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendPaymentReminder(String email, String residentName) {
        if (email != null && !email.isEmpty()) {
            String message = String.format("Enviando lembrete de pagamento para %s no e-mail %s", residentName, email);
            logger.info(message);
        } else {
            String message = String.format("Morador %s não possui um e-mail cadastrado para notificação.", residentName);
            logger.warn(message);
        }
    }

    public void notifyProprietary(String email, String message) {
        if (email != null && !email.isEmpty()) {
            logger.info("Notificando proprietário: {} - {}", email, message);
        } else {
            logger.warn("Proprietário sem e-mail cadastrado. Mensagem: {}", message);
        }
    }
}
