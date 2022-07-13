package com.tangerinedelivery.controller;

import com.tangerinedelivery.controller.dto.LoginDTO;
import com.tangerinedelivery.controller.dto.RegistrationDTO;
import com.tangerinedelivery.repo.RoleRepo;
import com.tangerinedelivery.repo.UserRepo;
import com.tangerinedelivery.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, AuthService authService, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.authService = authService;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO){
        return authService.register(registrationDTO);
    }

    @GetMapping("/registration/mailconfirmation")
    public ResponseEntity<String> confirmEmail(@RequestParam String token){
        return authService.enableUser(token);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginDTO loginDTO){
        return authService.sign(loginDTO);
    }

}
