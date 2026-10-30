package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}

