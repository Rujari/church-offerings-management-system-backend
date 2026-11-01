package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pledges")
public class PledgeController {

    private final PledgeService pledgeService;

    public PledgeController(PledgeService pledgeService) {
        this.pledgeService = pledgeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Pledge pledge) {
        String result = pledgeService.save(pledge);
        
        if (result.equals("PAST_DATE")) {
            return new ResponseEntity<>("Pledge date cannot be in the past", HttpStatus.BAD_REQUEST);
        } else if (result.equals("DATE_CONFLICT")) {
            return new ResponseEntity<>("Treasurer already collects a pledge on that date", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pledge>> getAll() {
        return new ResponseEntity<>(pledgeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Pledge> optionalPledge = pledgeService.getById(id);
        
        if (optionalPledge.isPresent()) {
            return new ResponseEntity<>(optionalPledge.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pledge with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Pledge pledge) {
        String result = pledgeService.update(id, pledge);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The pledge with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = pledgeService.delete(id);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The pledge with that id does not exist", HttpStatus.NOT_FOUND);
        } else if (result.equals("HAS_RECEIPTS")) {
            return new ResponseEntity<>("That pledge has receipts, delete them first", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Pledge>> getByStatus(@RequestParam PledgeStatus status) {
        List<Pledge> pledges = pledgeService.getPledgesByStatus(status);
        return new ResponseEntity<>(pledges, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Pledge>> getInDateRange(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        
        List<Pledge> pledges = pledgeService.getPledgesInDateRange(startDate, endDate);
        return new ResponseEntity<>(pledges, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam PledgeStatus status) {
        String result = pledgeService.updateStatus(id, status);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The pledge with that id does not exist", HttpStatus.NOT_FOUND);
        } else if (result.startsWith("INVALID_TRANSITION")) {
            String[] tokens = result.split(":");
            String current = tokens[1];
            String target = tokens[2];
            return new ResponseEntity<>("Cannot change status from " + current + " to " + target, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }  
        // --- Requirement F1: Get report summary rows ---
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return new ResponseEntity<>(pledgeService.getPledgeStatsByStatus(), HttpStatus.OK);
    }

    // --- Requirement F2: Patch bulk cancellation processing ---
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(
            @RequestParam UUID treasurerId, 
            @RequestParam String date) {
            
        LocalDate targetDate = LocalDate.parse(date);
        String confirmationMessage = pledgeService.cancelDayForTreasurer(treasurerId, targetDate);
        
        return new ResponseEntity<>(confirmationMessage, HttpStatus.OK);
    }

}
