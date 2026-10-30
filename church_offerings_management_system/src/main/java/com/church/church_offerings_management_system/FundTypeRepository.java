package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FundTypeRepository extends JpaRepository<FundType, UUID> {
}
