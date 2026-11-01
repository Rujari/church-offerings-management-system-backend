package com.church.church_offerings_management_system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund-types")
public class FundTypeController {

    private final FundTypeService fundTypeService;

    public FundTypeController(FundTypeService fundTypeService) {
        this.fundTypeService = fundTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody FundType fundType) {
        String result = fundTypeService.save(fundType);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FundType>> getAll() { 
        return new ResponseEntity<>(fundTypeService.getAll(), HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<FundType> optionalFundType = fundTypeService.getById(id);
        
        if (optionalFundType.isPresent()) {
            return new ResponseEntity<>(optionalFundType.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The fund type with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody FundType fundType) {
        String result = fundTypeService.update(id, fundType);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The fund type with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = fundTypeService.delete(id);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The fund type with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
        @GetMapping("/unused")
    public ResponseEntity<List<FundType>> getUnusedFundTypes() {
        return new ResponseEntity<>(fundTypeService.getUnusedFundTypes(), HttpStatus.OK);
    }

}
