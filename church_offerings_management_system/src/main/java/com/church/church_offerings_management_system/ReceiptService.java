package com.church.church_offerings_management_system;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final PledgeRepository pledgeRepository;

    
    public ReceiptService(ReceiptRepository receiptRepository, PledgeRepository pledgeRepository) {
        this.receiptRepository = receiptRepository;
        this.pledgeRepository = pledgeRepository;
    }

    
    public String save(UUID pledgeId, Receipt receipt) {
        Optional<Pledge> optionalPledge = pledgeRepository.findById(pledgeId);
        if (optionalPledge.isEmpty()) {
            return "PLEDGE_NOT_FOUND";
        }
        
        Pledge pledge = optionalPledge.get();
        if (pledge.getStatus() != PledgeStatus.FULFILLED) {
            return "NOT_FULFILLED";
        }
        
        receipt.setIssuedDate(LocalDate.now());
        receipt.setPledge(pledge);
        receiptRepository.save(receipt);
        
        return "Receipt saved successfully";
    }

    public List<Receipt> getAll() { 
        return receiptRepository.findAll(); 
    }


    public List<Receipt> findByMemberNumber(String memberNumber) {
        return receiptRepository.findByMemberNumber(memberNumber);
    }

    
    public String delete(UUID id) {
        if (!receiptRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        receiptRepository.deleteById(id);
        return "Receipt deleted successfully";
    }
}
