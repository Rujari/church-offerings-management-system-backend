package com.church.church_offerings_management_system;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String save(Member incoming) {
        memberRepository.save(incoming);
        return "Member saved successfully";
    }

    public List<Member> getAll() { 
        return memberRepository.findAll(); 
    }

    public Optional<Member> getById(UUID id) { 
        return memberRepository.findById(id); 
    }

    public String update(UUID id, Member incoming) {
        Optional<Member> existing = memberRepository.findById(id);
        if (existing.isEmpty()) {
            return "NOT_FOUND";
        }
        
        Member savedMember = existing.get();
        savedMember.setFirstName(incoming.getFirstName());
        savedMember.setLastName(incoming.getLastName());
        savedMember.setMemberNumber(incoming.getMemberNumber());
        savedMember.setPhone(incoming.getPhone());
        
        memberRepository.save(savedMember);
        return "Member updated successfully";
    }

    public String delete(UUID id) {
        if (!memberRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        
        memberRepository.deleteById(id);
        return "Member deleted successfully";
    }

    
      public List<Member> getMembersByLastName(String lastName) {
        return memberRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }



    public List<Member> getMembersOfTreasurer(UUID treasurerId) {
        return memberRepository.findDistinctMembersByTreasurerId(treasurerId);
    }
}
