package com.example.msresident.controller;

import com.example.msresident.model.Resident;
import com.example.msresident.model.ScheduledMessage;
import com.example.msresident.repository.ResidentRepository;
import com.example.msresident.service.MessageSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private MessageSchedulerService messageSchedulerService;

    // Message Scheduling Endpoints (put specific paths first to avoid routing conflicts)
    
    @PostMapping("/schedule-message")
    public ResponseEntity<ScheduledMessage> scheduleMessage(@RequestBody Map<String, Object> request) {
        try {
            // Support both old and new field names for better compatibility
            String title = (String) request.getOrDefault("title", 
                            (String) request.getOrDefault("subject", "Notification"));
            String message = (String) request.getOrDefault("message", 
                            (String) request.get("body"));
            String scheduledDateStr = (String) request.getOrDefault("scheduledDate", 
                                     (String) request.get("scheduledDateTime"));
            Object residentIdObj = request.get("residentId");
            
            if (message == null || scheduledDateStr == null) {
                return ResponseEntity.badRequest().build();
            }
            
            LocalDateTime scheduledDate = LocalDateTime.parse(scheduledDateStr);
            
            ScheduledMessage scheduledMessage;
            if (residentIdObj != null) {
                Long residentId = Long.valueOf(residentIdObj.toString());
                scheduledMessage = messageSchedulerService.scheduleMessageForRecipient(
                    title, message, residentId, scheduledDate, "Admin", "NOTIFICATION"
                );
            } else {
                scheduledMessage = messageSchedulerService.scheduleMessage(
                    title, message, "ALL", scheduledDate, "Admin", "NOTIFICATION"
                );
            }
            
            return ResponseEntity.ok(scheduledMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/scheduled-messages")
    public List<ScheduledMessage> getScheduledMessages() {
        return messageSchedulerService.getMessagesByStatus(ScheduledMessage.MessageStatus.SCHEDULED);
    }
    
    @GetMapping("/scheduled-messages/{id}")
    public ResponseEntity<ScheduledMessage> getScheduledMessage(@PathVariable Long id) {
        Optional<ScheduledMessage> message = messageSchedulerService.getMessageById(id);
        return message.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/scheduled-messages/{id}")
    public ResponseEntity<Map<String, Object>> cancelScheduledMessage(@PathVariable Long id) {
        boolean cancelled = messageSchedulerService.cancelMessage(id);
        if (cancelled) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Message cancelled successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Unable to cancel message"));
        }
    }

    // Standard CRUD Endpoints for Residents
    
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
