package com.evernorth.profilesetup.api.validate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evernorth.profilesetup.service.jwt.UserDetailsServiceImpl;
import com.evernorth.profilesetup.dto.LoginDTO;
import com.evernorth.profilesetup.service.database.entity.User;
import com.evernorth.profilesetup.service.database.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/validate/login")
@Slf4j
public class Login { 
    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @PostMapping
    @Async
    public ResponseEntity<Map<String, Object>> isLoginValid(@RequestBody LoginDTO payload) {
        Optional<User> userOptional = userRepository.findByMemberID(payload.getMemberID());
        Map<String, Object> response = new HashMap<>();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!userOptional.isPresent()){
            response.put("valid", false);
            response.put("message" , "Invalid MemberID");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            // return ResponseEntity.ok(response);

        } else {
            User user = userOptional.get();
            if(!passwordEncoder.matches(payload.getPassword(), user.getPassword())){
                response.put("valid", false);
                response.put("message", "Incorrect Password");
                // return ResponseEntity.ok(response);

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }


            // try {
            //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            //         payload.getMemberID(), payload.getPassword()
            //     ));
            //     UserDetails userDetails = userDetailsService.loadUserByUsername(user.getMemberID());
            //     // String jwt = jwtUtil
            // } catch (Exception e) {

            // }

            String token = "Hi";

            response.put("valid", true);
            response.put("token", token);

            return ResponseEntity.ok(response);

        }
    }
}
