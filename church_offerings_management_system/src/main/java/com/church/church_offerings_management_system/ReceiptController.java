package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID pledgeId, @RequestBody Receipt receipt) {
        String outcome = receiptService.save(pledgeId, receipt);
        
        if (outcome.equals("PLEDGE_NOT_FOUND")) {
            return new ResponseEntity<>("The pledge with that id does not exist", HttpStatus.NOT_FOUND);
        } else if (outcome.equals("NOT_FULFILLED")) {
            return new ResponseEntity<>("Receipts are only for FULFILLED pledges", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(outcome, HttpStatus.CREATED);
        }
    }

    
    @GetMapping("/member/{memberNumber}")
    public ResponseEntity<List<Receipt>> getByMemberNumber(@PathVariable String memberNumber) {
        List<Receipt> history = receiptService.findByMemberNumber(memberNumber);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

  
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String outcome = receiptService.delete(id);
        
        if (outcome.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The receipt with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(outcome, HttpStatus.OK);
        }
    }
}
