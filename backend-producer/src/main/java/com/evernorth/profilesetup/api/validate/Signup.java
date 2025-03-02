package com.evernorth.profilesetup.api.validate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evernorth.profilesetup.service.database.repository.MemberRepository;
import java.util.Optional;
import com.evernorth.profilesetup.service.database.entity.Member;



@RestController
@RequestMapping("/api/validate/signup")
public class Signup {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    @Async
    public ResponseEntity<Map<String, Object>> isSignupValid(@RequestParam String memberID, @RequestParam(required = false) String email, @RequestParam int stage) {

        
        Optional<Member> memberOptional = memberRepository.findByMemberID(memberID);
        Map<String, Object> response = new HashMap<>();
        if(stage == 1) {
            if(!memberOptional.isPresent()){
                response.put("exists", false);
                response.put("message", "MemberID is invalid!");
            } else {
                boolean active = memberOptional.get().isActive();
                if(active) {
                    response.put("exists", false); 
                    response.put("message", "Account already exists");
                } else {
                    response.put("exists", true);
                    response.put("message", "possible");
                }
            }
            return ResponseEntity.ok(response);
        }

        String DB_EMAIL = memberOptional.get().getEmail();
        String DB_MEMBERID = memberOptional.get().getMemberID();

        if(!DB_EMAIL.equals(email) || !DB_MEMBERID.equals(memberID)) {
            response.put("valid", false);
        } else {
            response.put("valid", true);
        }

        return ResponseEntity.ok(response);
    }
}
