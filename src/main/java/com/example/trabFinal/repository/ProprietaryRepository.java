package com.example.trabFinal.repository;

import com.example.trabFinal.model.Proprietary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaryRepository extends JpaRepository<Proprietary, Long> {
}
