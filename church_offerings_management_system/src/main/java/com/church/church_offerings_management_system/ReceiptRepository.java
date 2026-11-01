package com.church.church_offerings_management_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

   
    @Query("SELECT rcpt FROM Receipt rcpt WHERE rcpt.pledge.member.memberNumber = :memberNumber ORDER BY rcpt.issuedDate DESC")
    List<Receipt> findByMemberNumber(@Param("memberNumber") String memberNumber);

    
    boolean existsByPledge(Pledge pledge);
}
