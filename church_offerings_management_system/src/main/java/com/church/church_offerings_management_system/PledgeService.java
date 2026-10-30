package com.church.church_offerings_management_system;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PledgeService {

    private final PledgeRepository pledgeRepository;

    public PledgeService(PledgeRepository pledgeRepository) {
        this.pledgeRepository = pledgeRepository;
    }

    public String save(Pledge incoming) {
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
        if (!pledgeRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        pledgeRepository.deleteById(id);
        return "Pledge deleted successfully";
    }
}
