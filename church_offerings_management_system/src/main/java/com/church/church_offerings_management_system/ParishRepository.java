package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParishRepository extends JpaRepository<Parish, UUID> {

    // --- Required for Part C2 Query Logic Mapping ---
    @org.springframework.data.jpa.repository.Query("SELECT p FROM Parish p WHERE NOT EXISTS (SELECT t FROM Treasurer t WHERE t.parish = p) ORDER BY p.parishNumber ASC")
    List<Parish> findUnoccupiedParishes();

    // --- Required for D4 Parish Reassignment ---
    Optional<Parish> findByParishNumber(int parishNumber);
}

