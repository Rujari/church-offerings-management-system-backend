package com.church.church_offerings_management_system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PledgeService {

    private final PledgeRepository pledgeRepository;
    private final ReceiptRepository receiptRepository;

    public PledgeService(PledgeRepository pledgeRepository, ReceiptRepository receiptRepository) {
        this.pledgeRepository = pledgeRepository;
        this.receiptRepository = receiptRepository;
    }

    public String save(Pledge incoming) {
        if (incoming.getPledgeDate().isBefore(LocalDate.now())) {
            return "PAST_DATE";
        }
        
        boolean conflictExists = pledgeRepository.existsByTreasurerIdAndPledgeDateAndStatusNot(
            incoming.getTreasurer().getId(), 
            incoming.getPledgeDate(), 
            PledgeStatus.WITHDRAWN
        );
        
        if (conflictExists) {
            return "DATE_CONFLICT";
        }
        
        incoming.setStatus(PledgeStatus.PLEDGED);
        pledgeRepository.save(incoming);
        return "Pledge saved successfully";
    }

    public List<Pledge> getAll() { 
        return pledgeRepository.findAll(); 
    }

    public Optional<Pledge> getById(UUID id) { 
        return pledgeRepository.findById(id); 
    }

    public String update(UUID id, Pledge incoming) {
        Optional<Pledge> existing = pledgeRepository.findById(id);
        if (existing.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Pledge savedPledge = existing.get();
        savedPledge.setPledgeDate(incoming.getPledgeDate());
        savedPledge.setAmount(incoming.getAmount());
        savedPledge.setPurpose(incoming.getPurpose());
        
        if (incoming.getMember() != null) {
            savedPledge.setMember(incoming.getMember());
        }
        
        if (incoming.getTreasurer() != null) {
            savedPledge.setTreasurer(incoming.getTreasurer());
        }
        
        pledgeRepository.save(savedPledge);
        return "Pledge updated successfully";
    }

    public String delete(UUID id) {
        Optional<Pledge> optionalPledge = pledgeRepository.findById(id);
        if (optionalPledge.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Pledge pledge = optionalPledge.get();
        
        if (receiptRepository.existsByPledge(pledge)) {
            return "HAS_RECEIPTS";
        }
        
        pledgeRepository.deleteById(id);
        return "Pledge deleted successfully";
    }

    public List<Pledge> getPledgesByStatus(PledgeStatus status) {
        return pledgeRepository.findByStatusOrderByPledgeDateAsc(status);
    }

    public List<Pledge> getPledgesInDateRange(LocalDate start, LocalDate end) {
        return pledgeRepository.findByPledgeDateBetweenOrderByPledgeDateAsc(start, end);
    }

    public String updateStatus(UUID id, PledgeStatus newStatus) {
        Optional<Pledge> optionalPledge = pledgeRepository.findById(id);
        if (optionalPledge.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Pledge pledge = optionalPledge.get();
        PledgeStatus currentStatus = pledge.getStatus();
        
        boolean validTransition = false;
        
        if (currentStatus == PledgeStatus.PLEDGED) {
            if (newStatus == PledgeStatus.PARTIALLY_PAID || newStatus == PledgeStatus.WITHDRAWN) {
                validTransition = true;
            }
        } else if (currentStatus == PledgeStatus.PARTIALLY_PAID) {
            if (newStatus == PledgeStatus.FULFILLED || newStatus == PledgeStatus.WITHDRAWN) {
                validTransition = true;
            }
        }
        
        if (!validTransition) {
            return "INVALID_TRANSITION:" + currentStatus + ":" + newStatus;
        }
        
        pledge.setStatus(newStatus);
        pledgeRepository.save(pledge);
        return "Pledge status updated successfully";
    }

   
    public List<Object[]> getPledgeStatsByStatus() {
        return pledgeRepository.countPledgesPerStatus();
    }

    
    @Transactional
    public String cancelDayForTreasurer(UUID treasurerId, LocalDate date) {
        int updatedRowsCount = pledgeRepository.cancelPledgesForAbsentTreasurer(
            treasurerId, 
            date, 
            PledgeStatus.WITHDRAWN, 
            PledgeStatus.FULFILLED
        );
        
        return updatedRowsCount + " pledges cancelled";
    }
}
