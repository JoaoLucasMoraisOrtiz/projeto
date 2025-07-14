package com.example.msresident.service;

import com.example.msresident.model.ScheduledMessage;
import com.example.msresident.repository.ScheduledMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MessageSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageSchedulerService.class);
    
    @Autowired
    private ScheduledMessageRepository messageRepository;
    
    @Autowired
    private BillingNotificationService notificationService;
    
    /**
     * Agenda uma nova mensagem
     */
    public ScheduledMessage scheduleMessage(String title, String content, String recipientType, 
                                          LocalDateTime scheduledTime, String senderName, String messageType) {
        
        ScheduledMessage message = new ScheduledMessage(title, content, recipientType, 
                                                       scheduledTime, senderName, messageType);
        
        ScheduledMessage savedMessage = messageRepository.save(message);
        
        logger.info("Mensagem agendada com sucesso - ID: {}, Título: {}, Agendada para: {}", 
                   savedMessage.getId(), savedMessage.getTitle(), 
                   savedMessage.getScheduledTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        return savedMessage;
    }
    
    /**
     * Agenda uma mensagem para um destinatário específico
     */
    public ScheduledMessage scheduleMessageForRecipient(String title, String content, 
                                                       Long recipientId, LocalDateTime scheduledTime, 
                                                       String senderName, String messageType) {
        
        ScheduledMessage message = new ScheduledMessage(title, content, "SPECIFIC", 
                                                       scheduledTime, senderName, messageType);
        message.setRecipientId(recipientId);
        
        ScheduledMessage savedMessage = messageRepository.save(message);
        
        logger.info("Mensagem agendada para destinatário específico - ID: {}, Destinatário: {}, Agendada para: {}", 
                   savedMessage.getId(), recipientId, 
                   savedMessage.getScheduledTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        return savedMessage;
    }
    
    /**
     * Executa a cada minuto para verificar e enviar mensagens agendadas
     */
    @Scheduled(fixedRate = 60000) // 60 segundos
    public void processScheduledMessages() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledMessage> messagesToSend = messageRepository.findMessagesToSend(now);
        
        if (!messagesToSend.isEmpty()) {
            logger.info("Processando {} mensagens agendadas...", messagesToSend.size());
            
            for (ScheduledMessage message : messagesToSend) {
                try {
                    sendMessage(message);
                } catch (Exception e) {
                    logger.error("Erro ao enviar mensagem ID {}: {}", message.getId(), e.getMessage());
                    message.setStatus(ScheduledMessage.MessageStatus.FAILED);
                    messageRepository.save(message);
                }
            }
        }
    }
    
    /**
     * Envia uma mensagem agendada
     */
    private void sendMessage(ScheduledMessage message) {
        logger.info("Enviando mensagem ID {}: {}", message.getId(), message.getTitle());
        
        // Simular envio da mensagem
        switch (message.getRecipientType()) {
            case "ALL":
                sendToAll(message);
                break;
            case "RESIDENTS":
                sendToResidents(message);
                break;
            case "PROPRIETARIES":
                sendToProprietaries(message);
                break;
            case "SPECIFIC":
                sendToSpecificRecipient(message);
                break;
            default:
                throw new IllegalArgumentException("Tipo de destinatário inválido: " + message.getRecipientType());
        }
        
        // Marcar como enviada
        message.setStatus(ScheduledMessage.MessageStatus.SENT);
        message.setSentAt(LocalDateTime.now());
        messageRepository.save(message);
        
        logger.info("Mensagem ID {} enviada com sucesso às {}", 
                   message.getId(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
    
    private void sendToAll(ScheduledMessage message) {
        // Implementar envio para todos
        logger.info("Enviando para TODOS: {}", message.getTitle());
        // Aqui você integraria com o serviço de notificação real
    }
    
    private void sendToResidents(ScheduledMessage message) {
        // Implementar envio para moradores
        logger.info("Enviando para MORADORES: {}", message.getTitle());
        // Aqui você integraria com o serviço de notificação real
    }
    
    private void sendToProprietaries(ScheduledMessage message) {
        // Implementar envio para proprietários
        logger.info("Enviando para PROPRIETÁRIOS: {}", message.getTitle());
        // Aqui você integraria com o serviço de notificação real
    }
    
    private void sendToSpecificRecipient(ScheduledMessage message) {
        // Implementar envio para destinatário específico
        logger.info("Enviando para destinatário ID {}: {}", message.getRecipientId(), message.getTitle());
        // Aqui você integraria com o serviço de notificação real
    }
    
    /**
     * Lista todas as mensagens agendadas
     */
    public List<ScheduledMessage> getAllScheduledMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Busca mensagem por ID
     */
    public Optional<ScheduledMessage> getMessageById(Long id) {
        return messageRepository.findById(id);
    }
    
    /**
     * Cancela uma mensagem agendada
     */
    public boolean cancelMessage(Long messageId) {
        Optional<ScheduledMessage> messageOpt = messageRepository.findById(messageId);
        
        if (messageOpt.isPresent()) {
            ScheduledMessage message = messageOpt.get();
            
            if (message.getStatus() == ScheduledMessage.MessageStatus.SCHEDULED) {
                message.setStatus(ScheduledMessage.MessageStatus.CANCELLED);
                messageRepository.save(message);
                
                logger.info("Mensagem ID {} cancelada com sucesso", messageId);
                return true;
            } else {
                logger.warn("Tentativa de cancelar mensagem ID {} com status {}", messageId, message.getStatus());
                return false;
            }
        }
        
        logger.warn("Mensagem ID {} não encontrada para cancelamento", messageId);
        return false;
    }
    
    /**
     * Busca mensagens por status
     */
    public List<ScheduledMessage> getMessagesByStatus(ScheduledMessage.MessageStatus status) {
        return messageRepository.findByStatus(status);
    }
    
    /**
     * Conta mensagens pendentes
     */
    public long countPendingMessages() {
        return messageRepository.countPendingMessages(LocalDateTime.now());
    }
    
    /**
     * Busca mensagens vencidas
     */
    public List<ScheduledMessage> getOverdueMessages() {
        return messageRepository.findOverdueMessages(LocalDateTime.now());
    }
}