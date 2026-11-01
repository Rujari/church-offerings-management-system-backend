package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PledgeRepository extends JpaRepository<Pledge, UUID> {

    List<Pledge> findByStatusOrderByPledgeDateAsc(PledgeStatus status);

    List<Pledge> findByPledgeDateBetweenOrderByPledgeDateAsc(LocalDate startDate, LocalDate endDate);

    boolean existsByTreasurerIdAndPledgeDateAndStatusNot(UUID treasurerId, LocalDate pledgeDate, PledgeStatus status);

    // --- Requirement F1: Aggregate Status Summary Grouping ---
    @Query("SELECT p.status, COUNT(p) FROM Pledge p GROUP BY p.status")
    List<Object[]> countPledgesPerStatus();

    // --- Requirement F2: Bulk Status Update (No Java loop) ---
    @Modifying
    @Query("UPDATE Pledge p SET p.status = :withdrawnStatus WHERE p.treasurer.id = :treasurerId AND p.pledgeDate = :date AND p.status <> :fulfilledStatus")
    int cancelPledgesForAbsentTreasurer(
        @Param("treasurerId") UUID treasurerId, 
        @Param("date") LocalDate date,
        @Param("withdrawnStatus") PledgeStatus withdrawnStatus,
        @Param("fulfilledStatus") PledgeStatus fulfilledStatus
    );
}
