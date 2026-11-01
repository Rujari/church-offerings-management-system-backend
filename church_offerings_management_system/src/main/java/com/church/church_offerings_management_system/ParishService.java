package com.church.church_offerings_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ParishService {

    private final ParishRepository parishRepository;

    public ParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    public String save(Parish incoming) {
        parishRepository.save(incoming);
        return "Parish saved successfully";
    }

    public List<Parish> getAll() { 
        return parishRepository.findAll(); 
    }

    public Optional<Parish> getById(UUID id) { 
        return parishRepository.findById(id); 
    }

    public String update(UUID id, Parish incoming) {
        Optional<Parish> existing = parishRepository.findById(id);
        if (existing.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Parish savedParish = existing.get();
        savedParish.setParishNumber(incoming.getParishNumber());
        savedParish.setName(incoming.getName());
        parishRepository.save(savedParish);
        
        return "Parish updated successfully";
    }

    public String delete(UUID id) {
        if (!parishRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        parishRepository.deleteById(id);
        return "Parish deleted successfully";
    }
        public List<Parish> getFreeParishes() {
        return parishRepository.findUnoccupiedParishes();
    }

}
