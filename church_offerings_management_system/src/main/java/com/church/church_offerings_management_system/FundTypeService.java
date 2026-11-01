package com.church.church_offerings_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class FundTypeService {

    private final FundTypeRepository fundTypeRepository;

    public FundTypeService(FundTypeRepository fundTypeRepository) {
        this.fundTypeRepository = fundTypeRepository;
    }

    public String save(FundType incoming) {
        fundTypeRepository.save(incoming);
        return "Fund type saved successfully";
    }

    public List<FundType> getAll() { 
        return fundTypeRepository.findAll(); 
    }

    public Optional<FundType> getById(UUID id) { 
        return fundTypeRepository.findById(id); 
    }

    public String update(UUID id, FundType incoming) {
        Optional<FundType> existing = fundTypeRepository.findById(id);
        if (existing.isEmpty()) {
            return "NOT_FOUND";
        }
        
        FundType savedType = existing.get();
        savedType.setName(incoming.getName());
        fundTypeRepository.save(savedType);
        
        return "Fund type updated successfully";
    }

    public String delete(UUID id) {
        if (!fundTypeRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        fundTypeRepository.deleteById(id);
        return "Fund type deleted successfully";
    }
        public List<FundType> getUnusedFundTypes() {
        return fundTypeRepository.findUnusedFundTypes();
    }

}
