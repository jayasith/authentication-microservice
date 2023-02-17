package com.example.authentication.controller;

import com.example.authentication.controller.request.LoginRequest;
import com.example.authentication.controller.request.SignUpRequest;
import com.example.authentication.controller.response.SignupResponse;
import com.example.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public SignupResponse login(@RequestBody LoginRequest loginRequest) {
        String token = userService.userLogin(loginRequest);
        return SignupResponse.builder()
                .token(token)
                .build();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public SignupResponse signup(@RequestBody SignUpRequest signUpRequest) {
        String token = userService.userSignUp(signUpRequest);
        return SignupResponse.builder()
                .token(token)
                .build();
    }

}
