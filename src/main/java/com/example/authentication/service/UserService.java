package com.example.authentication.service;

import com.example.authentication.controller.request.LoginRequest;
import com.example.authentication.controller.request.SignUpRequest;
import com.example.authentication.persistance.UserPersistence;
import com.example.authentication.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserService {

    private final UserPersistence userPersistence;
    @Autowired
    public UserService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public boolean userLogin(LoginRequest loginRequest){
        User user = userPersistence.getUserByUsername(loginRequest.getUsername());

        String a;
        if(passwordMatched(loginRequest.getPassword(), user.getPassword())){
            return true;
        }else {
            return false;
        }
    }

    public void userSignUp(SignUpRequest signUpRequest){
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(userPasswordHash(signUpRequest.getPassword()))
                .build();

        userPersistence.saveUser(user);
    }



    public boolean passwordMatched(String rawPassword, String hashPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword,hashPassword);
    }

    public String userPasswordHash(String rawPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }
}
