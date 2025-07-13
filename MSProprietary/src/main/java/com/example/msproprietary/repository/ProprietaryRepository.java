package com.example.msproprietary.repository;

import com.example.msproprietary.model.Proprietary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaryRepository extends JpaRepository<Proprietary, Long> {
}
