package com.example.msresident.controller;

import com.example.msresident.model.ScheduledMessage;
import com.example.msresident.service.MessageSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    private MessageSchedulerService messageSchedulerService;
    
    /**
     * Agenda uma nova mensagem
     */
    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleMessage(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String content = (String) request.get("content");
            String recipientType = (String) request.get("recipientType");
            String scheduledTimeStr = (String) request.get("scheduledTime");
            String senderName = (String) request.get("senderName");
            String messageType = (String) request.get("messageType");
            
            // Validações básicas
            if (title == null || content == null || recipientType == null || scheduledTimeStr == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Campos obrigatórios: title, content, recipientType, scheduledTime"));
            }
            
            // Parse da data/hora
            LocalDateTime scheduledTime = LocalDateTime.parse(scheduledTimeStr);
            
            // Verificar se a data não é no passado
            if (scheduledTime.isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "A data de agendamento deve ser no futuro"));
            }
            
            // Valores padrão
            if (senderName == null) senderName = "Sistema";
            if (messageType == null) messageType = "INFO";
            
            ScheduledMessage message;
            
            // Se for para destinatário específico
            if ("SPECIFIC".equals(recipientType) && request.containsKey("recipientId")) {
                Long recipientId = Long.valueOf(request.get("recipientId").toString());
                message = messageSchedulerService.scheduleMessageForRecipient(
                    title, content, recipientId, scheduledTime, senderName, messageType);
            } else {
                message = messageSchedulerService.scheduleMessage(
                    title, content, recipientType, scheduledTime, senderName, messageType);
            }
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Mensagem agendada com sucesso",
                "messageId", message.getId(),
                "scheduledTime", message.getScheduledTime()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao agendar mensagem: " + e.getMessage()));
        }
    }
    
    /**
     * Lista todas as mensagens agendadas
     */
    @GetMapping
    public ResponseEntity<List<ScheduledMessage>> getAllMessages() {
        List<ScheduledMessage> messages = messageSchedulerService.getAllScheduledMessages();
        return ResponseEntity.ok(messages);
    }
    
    /**
     * Busca mensagem por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable Long id) {
        Optional<ScheduledMessage> message = messageSchedulerService.getMessageById(id);
        
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Cancela uma mensagem agendada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelMessage(@PathVariable Long id) {
        boolean cancelled = messageSchedulerService.cancelMessage(id);
        
        if (cancelled) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Mensagem cancelada com sucesso"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Não foi possível cancelar a mensagem. Verifique se ela existe e está agendada."
            ));
        }
    }
    
    /**
     * Lista mensagens por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ScheduledMessage>> getMessagesByStatus(@PathVariable String status) {
        try {
            ScheduledMessage.MessageStatus messageStatus = ScheduledMessage.MessageStatus.valueOf(status.toUpperCase());
            List<ScheduledMessage> messages = messageSchedulerService.getMessagesByStatus(messageStatus);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Conta mensagens pendentes
     */
    @GetMapping("/count/pending")
    public ResponseEntity<Map<String, Long>> countPendingMessages() {
        long count = messageSchedulerService.countPendingMessages();
        return ResponseEntity.ok(Map.of("pendingMessages", count));
    }
    
    /**
     * Lista mensagens vencidas
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<ScheduledMessage>> getOverdueMessages() {
        List<ScheduledMessage> messages = messageSchedulerService.getOverdueMessages();
        return ResponseEntity.ok(messages);
    }
    
    /**
     * Agenda mensagem rápida (para demonstração)
     */
    @PostMapping("/schedule/quick")
    public ResponseEntity<?> scheduleQuickMessage(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(defaultValue = "ALL") String recipientType,
            @RequestParam(defaultValue = "5") int minutesFromNow,
            @RequestParam(defaultValue = "Sistema") String senderName,
            @RequestParam(defaultValue = "INFO") String messageType) {
        
        try {
            LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(minutesFromNow);
            
            ScheduledMessage message = messageSchedulerService.scheduleMessage(
                title, content, recipientType, scheduledTime, senderName, messageType);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Mensagem agendada para " + minutesFromNow + " minutos",
                "messageId", message.getId(),
                "scheduledTime", message.getScheduledTime()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao agendar mensagem: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint de saúde para o sistema de mensagens
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        long pendingCount = messageSchedulerService.countPendingMessages();
        long overdueCount = messageSchedulerService.getOverdueMessages().size();
        
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "MessageScheduler",
            "pendingMessages", pendingCount,
            "overdueMessages", overdueCount,
            "timestamp", LocalDateTime.now()
        ));
    }
}