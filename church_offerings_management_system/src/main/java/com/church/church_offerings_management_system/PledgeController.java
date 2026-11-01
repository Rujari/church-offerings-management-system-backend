package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(result, HttpStatus.CREATED);
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
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
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

}
