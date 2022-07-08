package com.tangerinedelivery.controllers;

import com.tangerinedelivery.DTOs.LoginDTO;
import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.repos.RoleRepo;
import com.tangerinedelivery.repos.UserRepo;
import com.tangerinedelivery.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, AuthService authService, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.authService = authService;
    }

    @ApiOperation("Registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO){
        return authService.register(registrationDTO);
    }

    @ApiOperation("Sign in")
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginDTO loginDTO){
        return authService.sign(loginDTO);
    }

}
