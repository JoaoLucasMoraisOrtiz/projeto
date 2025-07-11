package com.example.trabFinal.controller;

import com.example.trabFinal.model.RentInvoice;
import com.example.trabFinal.model.Resident;
import com.example.trabFinal.model.Proprietary;
import com.example.trabFinal.repository.ResidentRepository;
import com.example.trabFinal.repository.ProprietaryRepository;
import com.example.trabFinal.service.RentInvoiceService;
import com.example.trabFinal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rent")
public class RentController {
    @Autowired
    private RentInvoiceService rentInvoiceService;
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private ProprietaryRepository proprietaryRepository;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/invoices")
    public List<RentInvoice> getAllInvoices() {
        return rentInvoiceService.getAllInvoices();
    }

    @PostMapping("/invoice")
    public RentInvoice createInvoice(@RequestParam Long residentId,
                                     @RequestParam Long proprietaryId,
                                     @RequestParam Double amount,
                                     @RequestParam String dueDate) {
        Resident resident = residentRepository.findById(residentId).orElseThrow();
        Proprietary proprietary = proprietaryRepository.findById(proprietaryId).orElseThrow();
        RentInvoice invoice = rentInvoiceService.createInvoice(resident, proprietary, amount, LocalDate.parse(dueDate));
        notificationService.sendPaymentReminder(resident.getContact().getEmail(), resident.getName());
        notificationService.notifyProprietary(proprietary.getContact().getEmail(), "Cobrança de aluguel gerada para o residente " + resident.getName());
        return invoice;
    }
}
