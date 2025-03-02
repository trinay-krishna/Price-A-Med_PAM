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

import com.evernorth.profilesetup.service.redis.OtpQuery;
import com.evernorth.profilesetup.service.redis.entity.Otp;

@RestController
@RequestMapping("/api/validate/otp")
public class OTP {

    @Autowired
    private OtpQuery otpQuery;

    @GetMapping
    @Async
    public ResponseEntity<Map<String, Object>> isSignupValid(@RequestParam String taskID, @RequestParam String otp) {
        Map<String, Object> response = new HashMap<>();

        Otp ob = otpQuery.findById(taskID);
        if(ob == null) {
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }

        boolean valid = ob.getOtp().equals(otp);
        System.out.println(ob.getOtp() + " " + otp + " " + valid);

        response.put("valid", valid);

        System.out.println("OTP validity: " + valid);
        return ResponseEntity.ok(response);
    }
    
}
