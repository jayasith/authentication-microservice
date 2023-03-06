package com.example.authentication.service;

import com.example.authentication.auth.JwtService;
import com.example.authentication.controller.request.LoginRequest;
import com.example.authentication.controller.request.SignUpRequest;
import com.example.authentication.feign.NotificationFeign;
import com.example.authentication.persistance.UserPersistence;
import com.example.authentication.persistance.entity.Role;
import com.example.authentication.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;

    @Value("${rabbitmq.routing-key.internal-notification}")
    private String routingKey;

    private final UserPersistence userPersistence;

    private final AuthenticationManager authenticationManager;
    private final NotificationFeign notificationFeign;

    private final JwtService jwtService;

    @Autowired
    public UserService(UserPersistence userPersistence, AuthenticationManager authenticationManager, NotificationFeign notificationFeign, JwtService jwtService) {
        this.userPersistence = userPersistence;
        this.authenticationManager = authenticationManager;
        this.notificationFeign = notificationFeign;
        this.jwtService = jwtService;
    }

    public String userLogin(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User user = userPersistence.getUserByUsername(loginRequest.getUsername());
        return jwtService.generateToken(user);
    }

    public String userSignUp(SignUpRequest signUpRequest) {

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(userPasswordHash(signUpRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userPersistence.saveUser(user);
        notificationFeign.pushNotification("welcome");
        return jwtService.generateToken(user);
    }


    public boolean passwordMatched(String rawPassword, String hashPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, hashPassword);
    }

    public String userPasswordHash(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }
}
