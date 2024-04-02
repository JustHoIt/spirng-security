package com.example.spirngsecurityinaction.service;


import com.example.spirngsecurityinaction.Entity.UserEntity;
import com.example.spirngsecurityinaction.Repository.UserRepository;
import com.example.spirngsecurityinaction.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean signUpProcess(SignUpDTO signUpDTO) {

        //db에 동링한 username이 가진 회원이 존재하는지? Exception 처리 구현하기
        boolean isUser = userRepository.existsByUsername(signUpDTO.getUsername());

        if (isUser) {
            return false;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setRole("ROLE_ADMIN");
        userRepository.save(userEntity);

        return true;
    }

    public String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        GrantedAuthority authority = authorities.iterator().next();

        return authority.getAuthority();
    }
}
