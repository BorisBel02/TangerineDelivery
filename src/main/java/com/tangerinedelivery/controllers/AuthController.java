package com.tangerinedelivery.controllers;

import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.RoleRepo;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO){

        if(userRepo.existsByEmail(registrationDTO.getEmail())){
            return new ResponseEntity<>("User with that email already exists", HttpStatus.BAD_REQUEST);
        }
        if(!(containsOnlyLetters(registrationDTO.getFirstName()) && containsOnlyLetters(registrationDTO.getMiddleName()) && containsOnlyLetters(registrationDTO.getLastName()))){
            return new ResponseEntity<>("Name must contain only letters", HttpStatus.BAD_REQUEST);
        }
        if(registrationDTO.getFirstName().length() > 254 || registrationDTO.getMiddleName().length() > 254 || registrationDTO.getLastName().length() > 254){
            return new ResponseEntity<>("Name is too large", HttpStatus.BAD_REQUEST);
        }
        if(registrationDTO.getPassword().length() < 6){
            return new ResponseEntity<>("Password is too short", HttpStatus.BAD_REQUEST);
        }

        UserEntity newUser = new UserEntity();

        newUser.setEmail(registrationDTO.getEmail());
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setMiddleName(registrationDTO.getMiddleName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        userRepo.save(newUser);

        return new ResponseEntity<>("User registrated successfully", HttpStatus.OK);
    }

    Boolean containsOnlyLetters(String str){
        char[] symbolsArray = str.toCharArray();
        for(char ch : symbolsArray){
            if(!Character.isLetter(ch)){
                return false;
            }
        }
        return true;
    }


}
