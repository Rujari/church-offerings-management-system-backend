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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/treasurers")
public class TreasurerController {

    private final TreasurerService treasurerService;

    public TreasurerController(TreasurerService treasurerService) {
        this.treasurerService = treasurerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Treasurer treasurer) {
        String result = treasurerService.save(treasurer);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Treasurer>> getAll() { 
        return new ResponseEntity<>(treasurerService.getAll(), HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Treasurer> optionalTreasurer = treasurerService.getById(id);
        
        if (optionalTreasurer.isPresent()) {
            return new ResponseEntity<>(optionalTreasurer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The treasurer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Treasurer treasurer) {
        String result = treasurerService.update(id, treasurer);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The treasurer with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = treasurerService.delete(id);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The treasurer with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
        @GetMapping("/by-fund-type")
    public ResponseEntity<List<Treasurer>> getByFundType(@RequestParam String name) {
        return new ResponseEntity<>(treasurerService.getTreasurersByFundType(name), HttpStatus.OK);
    }
    // --- Requirement D3: Put mapping link to update relationship association ---
    @PutMapping("/{treasurerId}/fund-types/{fundTypeId}")
    public ResponseEntity<String> addFundType(@PathVariable UUID treasurerId, @PathVariable UUID fundTypeId) {
        String response = treasurerService.addFundType(treasurerId, fundTypeId);
        
        if (response.equals("TREASURER_NOT_FOUND")) {
            return new ResponseEntity<>("The treasurer with that id does not exist", HttpStatus.NOT_FOUND);
        } else if (response.equals("FUND_TYPE_NOT_FOUND")) {
            return new ResponseEntity<>("That fund type does not exist", HttpStatus.NOT_FOUND);
        } else if (response.equals("ALREADY_ASSIGNED")) {
            return new ResponseEntity<>("The treasurer already has that fund type", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}

