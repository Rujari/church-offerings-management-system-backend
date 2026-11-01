package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/parishes")
public class ParishController {

    private final ParishService parishService;

    public ParishController(ParishService parishService) {
        this.parishService = parishService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Parish parish) {
        String result = parishService.save(parish);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parish>> getAll() { 
        return new ResponseEntity<>(parishService.getAll(), HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Parish> optionalParish = parishService.getById(id);
        
        if (optionalParish.isPresent()) {
            return new ResponseEntity<>(optionalParish.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The parish with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Parish parish) {
        String result = parishService.update(id, parish);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The parish with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = parishService.delete(id);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The parish with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
        @GetMapping("/free")
    public ResponseEntity<List<Parish>> getFreeParishes() {
        return new ResponseEntity<>(parishService.getFreeParishes(), HttpStatus.OK);
    }

}
