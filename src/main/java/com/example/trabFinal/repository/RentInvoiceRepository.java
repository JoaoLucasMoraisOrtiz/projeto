package com.example.trabFinal.repository;

import com.example.trabFinal.model.RentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentInvoiceRepository extends JpaRepository<RentInvoice, Long> {
}
