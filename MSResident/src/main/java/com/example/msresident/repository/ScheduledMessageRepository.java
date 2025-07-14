package com.example.msresident.repository;

import com.example.msresident.model.ScheduledMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, Long> {
    
    /**
     * Busca mensagens que devem ser enviadas agora
     */
    @Query("SELECT sm FROM ScheduledMessage sm WHERE sm.scheduledTime <= :now AND sm.status = 'SCHEDULED'")
    List<ScheduledMessage> findMessagesToSend(@Param("now") LocalDateTime now);
    
    /**
     * Busca mensagens por status
     */
    List<ScheduledMessage> findByStatus(ScheduledMessage.MessageStatus status);
    
    /**
     * Busca mensagens por tipo de destinatário
     */
    List<ScheduledMessage> findByRecipientType(String recipientType);
    
    /**
     * Busca mensagens de um destinatário específico
     */
    List<ScheduledMessage> findByRecipientId(Long recipientId);
    
    /**
     * Busca mensagens agendadas entre duas datas
     */
    @Query("SELECT sm FROM ScheduledMessage sm WHERE sm.scheduledTime BETWEEN :start AND :end")
    List<ScheduledMessage> findByScheduledTimeBetween(@Param("start") LocalDateTime start, 
                                                     @Param("end") LocalDateTime end);
    
    /**
     * Busca mensagens por tipo
     */
    List<ScheduledMessage> findByMessageType(String messageType);
    
    /**
     * Conta mensagens pendentes
     */
    @Query("SELECT COUNT(sm) FROM ScheduledMessage sm WHERE sm.status = 'SCHEDULED' AND sm.scheduledTime > :now")
    long countPendingMessages(@Param("now") LocalDateTime now);
    
    /**
     * Busca mensagens criadas por um remetente específico
     */
    List<ScheduledMessage> findBySenderName(String senderName);
    
    /**
     * Busca mensagens ordenadas por data de agendamento
     */
    List<ScheduledMessage> findByOrderByScheduledTimeAsc();
    
    /**
     * Busca mensagens vencidas (que deveriam ter sido enviadas)
     */
    @Query("SELECT sm FROM ScheduledMessage sm WHERE sm.scheduledTime < :now AND sm.status = 'SCHEDULED'")
    List<ScheduledMessage> findOverdueMessages(@Param("now") LocalDateTime now);
}
