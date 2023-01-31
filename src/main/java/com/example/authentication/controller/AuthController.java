package com.example.authentication.controller;

import com.example.authentication.controller.request.LoginRequest;
import com.example.authentication.controller.request.SignUpRequest;
import com.example.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String login(@RequestBody LoginRequest loginRequest){
       boolean result =  userService.userLogin(loginRequest);
       if(result){
           return "login successful";
       }else{
           return "login fail";
       }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void signup(@RequestBody SignUpRequest signUpRequest){
        userService.userSignUp(signUpRequest);
    }
}
