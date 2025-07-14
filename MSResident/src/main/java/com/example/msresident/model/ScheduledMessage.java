package com.example.msresident.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_messages")
public class ScheduledMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String content;
    
    @Column(nullable = false)
    private String recipientType; // ALL, RESIDENTS, PROPRIETARIES, SPECIFIC
    
    @Column
    private Long recipientId; // null for ALL, RESIDENTS, PROPRIETARIES
    
    @Column(nullable = false)
    private LocalDateTime scheduledTime;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private LocalDateTime sentAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;
    
    @Column
    private String senderName;
    
    @Column
    private String messageType; // INFO, WARNING, ALERT, PAYMENT_REMINDER
    
    public enum MessageStatus {
        SCHEDULED, SENT, FAILED, CANCELLED
    }
    
    // Constructors
    public ScheduledMessage() {
        this.createdAt = LocalDateTime.now();
        this.status = MessageStatus.SCHEDULED;
    }
    
    public ScheduledMessage(String title, String content, String recipientType, 
                           LocalDateTime scheduledTime, String senderName, String messageType) {
        this();
        this.title = title;
        this.content = content;
        this.recipientType = recipientType;
        this.scheduledTime = scheduledTime;
        this.senderName = senderName;
        this.messageType = messageType;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getRecipientType() {
        return recipientType;
    }
    
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    
    public Long getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public MessageStatus getStatus() {
        return status;
    }
    
    public void setStatus(MessageStatus status) {
        this.status = status;
    }
    
    public String getSenderName() {
        return senderName;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    @Override
    public String toString() {
        return "ScheduledMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", recipientType='" + recipientType + '\'' +
                ", scheduledTime=" + scheduledTime +
                ", status=" + status +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
