package com.church.church_offerings_management_system;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParishRepository extends JpaRepository<Parish, UUID> {

    @Query("SELECT p FROM Parish p WHERE NOT EXISTS (SELECT t FROM Treasurer t WHERE t.parish = p) ORDER BY p.parishNumber ASC")
    List<Parish> findUnoccupiedParishes();
}


