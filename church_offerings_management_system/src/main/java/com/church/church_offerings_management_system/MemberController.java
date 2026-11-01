package com.church.church_offerings_management_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final TreasurerService treasurerService;

    // The human constructor safely maps both dependencies side-by-side
    public MemberController(MemberService memberService, TreasurerService treasurerService) {
        this.memberService = memberService;
        this.treasurerService = treasurerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Member member) {
        String result = memberService.save(member);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAll() {
        return new ResponseEntity<>(memberService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Member> optionalMember = memberService.getById(id);
        
        if (optionalMember.isPresent()) {
            return new ResponseEntity<>(optionalMember.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The member with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Member member) {
        String result = memberService.update(id, member);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The member with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = memberService.delete(id);
        
        if (result.equals("NOT_FOUND")) {
            return new ResponseEntity<>("The member with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Member>> getByLastName(@RequestParam String lastName) {
        List<Member> members = memberService.getMembersByLastName(lastName);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    
    @GetMapping("/of-treasurer/{treasurerId}")
    public ResponseEntity<?> getMembersOfTreasurer(@PathVariable UUID treasurerId) {
        if (treasurerService.getById(treasurerId).isEmpty()) {
            return new ResponseEntity<>("The treasurer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        
        List<Member> members = memberService.getMembersOfTreasurer(treasurerId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
