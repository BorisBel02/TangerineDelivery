package com.tangerinedelivery.services;

import com.tangerinedelivery.DTOs.LoginDTO;
import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public ResponseEntity<?> register(RegistrationDTO registrationDTO){
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
    public ResponseEntity<String> sign(LoginDTO loginDTO){
        if(!userRepo.existsByEmail(loginDTO.getEmail())){
            return new ResponseEntity<>("Wrong email", HttpStatus.I_AM_A_TEAPOT);//ХЫЫЫЫЫЫЫ
        }
        if(userRepo.findUserEntityByEmail(loginDTO.getEmail()).getPassword().equals(passwordEncoder.encode(loginDTO.getPassword()))){
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
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
