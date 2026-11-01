package com.church.church_offerings_management_system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TreasurerService {

    private final TreasurerRepository treasurerRepository;
    private final FundTypeRepository fundTypeRepository;

   
    public TreasurerService(TreasurerRepository treasurerRepository, FundTypeRepository fundTypeRepository) {
        this.treasurerRepository = treasurerRepository;
        this.fundTypeRepository = fundTypeRepository;
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

    @Transactional
    public String addFundType(UUID treasurerId, UUID fundTypeId) {
        Optional<Treasurer> optionalTreasurer = treasurerRepository.findById(treasurerId);
        if (optionalTreasurer.isEmpty()) {
            return "TREASURER_NOT_FOUND";
        }
        
        Optional<FundType> optionalFundType = fundTypeRepository.findById(fundTypeId);
        if (optionalFundType.isEmpty()) {
            return "FUND_TYPE_NOT_FOUND";
        }

        Treasurer treasurer = optionalTreasurer.get();
        
        for (FundType type : treasurer.getFundTypes()) {
            if (type.getId().equals(fundTypeId)) {
                return "ALREADY_ASSIGNED";
            }
        }

        treasurer.getFundTypes().add(optionalFundType.get());
        treasurerRepository.save(treasurer);
        
        return "Fund type added to the treasurer";
    }
}
