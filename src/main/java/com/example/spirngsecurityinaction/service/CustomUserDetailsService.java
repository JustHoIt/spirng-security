package com.example.spirngsecurityinaction.service;

import com.example.spirngsecurityinaction.Entity.UserEntity;
import com.example.spirngsecurityinaction.Repository.UserRepository;
import com.example.spirngsecurityinaction.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity != null) {
            return new CustomUserDetails(userEntity);
        }
        return null;
    }
}
