package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PledgeRepository extends JpaRepository<Pledge, UUID> {

    List<Pledge> findByStatusOrderByPledgeDateAsc(PledgeStatus status);

    List<Pledge> findByPledgeDateBetweenOrderByPledgeDateAsc(LocalDate startDate, LocalDate endDate);

    
    boolean existsByTreasurerIdAndPledgeDateAndStatusNot(UUID treasurerId, LocalDate pledgeDate, PledgeStatus status);
}


