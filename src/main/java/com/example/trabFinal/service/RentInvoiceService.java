package com.example.trabFinal.service;

import com.example.trabFinal.model.RentInvoice;
import com.example.trabFinal.model.Resident;
import com.example.trabFinal.model.Proprietary;
import com.example.trabFinal.repository.RentInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentInvoiceService {
    @Autowired
    private RentInvoiceRepository rentInvoiceRepository;

    public RentInvoice createInvoice(Resident resident, Proprietary proprietary, Double amount, LocalDate dueDate) {
        RentInvoice invoice = new RentInvoice();
        invoice.setResident(resident);
        invoice.setProprietary(proprietary);
        invoice.setAmount(amount);
        invoice.setDueDate(dueDate);
        invoice.setPaid(false);
        return rentInvoiceRepository.save(invoice);
    }

    public List<RentInvoice> getAllInvoices() {
        return rentInvoiceRepository.findAll();
    }

    public RentInvoice getInvoice(Long id) {
        return rentInvoiceRepository.findById(id).orElse(null);
    }

    public RentInvoice save(RentInvoice invoice) {
        return rentInvoiceRepository.save(invoice);
    }
}
