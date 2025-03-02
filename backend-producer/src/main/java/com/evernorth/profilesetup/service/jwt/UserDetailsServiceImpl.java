package com.evernorth.profilesetup.service.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.evernorth.profilesetup.service.database.entity.User;
import com.evernorth.profilesetup.service.database.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String memberID) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByMemberID(memberID);
        User user = userOptional.get();
        if(user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                   .username(user.getMemberID())
                   .password(user.getPassword())
                   .roles(user.getRole())
                   .build();
        }
        throw new UsernameNotFoundException("User not found with MemberID: " + memberID);

    }
    
}
