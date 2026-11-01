package com.church.church_offerings_management_system;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FundTypeRepository extends JpaRepository<FundType, UUID> {

    @Query("SELECT f FROM FundType f WHERE f.treasurers IS EMPTY")
    List<FundType> findUnusedFundTypes();
}

