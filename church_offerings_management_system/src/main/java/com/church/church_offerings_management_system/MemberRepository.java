package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT p.member FROM Pledge p WHERE p.treasurer.id = :treasurerId")
    List<Member> findDistinctMembersByTreasurerId(@Param("treasurerId") UUID treasurerId);
}
