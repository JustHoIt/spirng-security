package com.example.spirngsecurityinaction.service;


import com.example.spirngsecurityinaction.Entity.UserEntity;
import com.example.spirngsecurityinaction.Repository.UserRepository;
import com.example.spirngsecurityinaction.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean signUpProcess(SignUpDTO signUpDTO){

        //db에 동링한 username이 가진 회원이 존재하는지? Exception 처리 구현하기

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setRole("ROLE_USER");
        userRepository.save(userEntity);

        return true;
    }
}
