package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface TreasurerRepository extends JpaRepository<Treasurer, UUID> {

    @Query("SELECT t FROM Treasurer t JOIN t.fundTypes f WHERE LOWER(f.name) = LOWER(:name)")
    List<Treasurer> findTreasurersByFundTypeNameIgnoreCase(@Param("name") String name);
}

