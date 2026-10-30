package com.church.church_offerings_management_system;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParishRepository extends JpaRepository<Parish, UUID> {
}

