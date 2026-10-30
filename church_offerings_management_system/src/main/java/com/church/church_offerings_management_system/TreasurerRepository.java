package com.church.church_offerings_management_system;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreasurerRepository extends JpaRepository<Treasurer, UUID> {
}
