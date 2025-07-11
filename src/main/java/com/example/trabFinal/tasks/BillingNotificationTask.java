package com.example.trabFinal.tasks;

import com.example.trabFinal.model.Resident;
import com.example.trabFinal.repository.ResidentRepository;
import com.example.trabFinal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillingNotificationTask {
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 10 5 * ?")
    public void sendMonthlyPaymentReminders() {
        List<Resident> residents = residentRepository.findAll();
        for (Resident resident : residents) {
            notificationService.sendPaymentReminder(
                resident.getContact().getEmail(),
                resident.getName()
            );
        }
    }
}
