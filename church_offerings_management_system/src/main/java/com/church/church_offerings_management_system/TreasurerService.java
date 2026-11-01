package com.church.church_offerings_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TreasurerService {

    private final TreasurerRepository treasurerRepository;

    public TreasurerService(TreasurerRepository treasurerRepository) {
        this.treasurerRepository = treasurerRepository;
    }

    public String save(Treasurer incoming) {
        treasurerRepository.save(incoming);
        return "Treasurer saved successfully";
    }

    public List<Treasurer> getAll() { 
        return treasurerRepository.findAll(); 
    }

    public Optional<Treasurer> getById(UUID id) { 
        return treasurerRepository.findById(id); 
    }

    public String update(UUID id, Treasurer incoming) {
        Optional<Treasurer> existing = treasurerRepository.findById(id);
        if (existing.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Treasurer savedTreasurer = existing.get();
        savedTreasurer.setFirstName(incoming.getFirstName());
        savedTreasurer.setLastName(incoming.getLastName());
        savedTreasurer.setPhone(incoming.getPhone());
        
        if (incoming.getParish() != null) {
            savedTreasurer.setParish(incoming.getParish());
        }
        if (incoming.getFundTypes() != null) {
            savedTreasurer.setFundTypes(incoming.getFundTypes());
        }
        
        treasurerRepository.save(savedTreasurer);
        return "Treasurer updated successfully";
    }

    public String delete(UUID id) {
        if (!treasurerRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        treasurerRepository.deleteById(id);
        return "Treasurer deleted successfully";
    }
        public List<Treasurer> getTreasurersByFundType(String name) {
        return treasurerRepository.findTreasurersByFundTypeNameIgnoreCase(name);
    }

}
