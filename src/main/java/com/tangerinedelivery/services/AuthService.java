package com.tangerinedelivery.services;

import com.tangerinedelivery.DTOs.LoginDTO;
import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.entities.VerificationToken;
import com.tangerinedelivery.repos.TokenRepo;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JavaMailSender mailSender;

    @Autowired
    public AuthService(UserRepo userRepo, TokenRepo tokenRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JavaMailSender mailSender){
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.mailSender = mailSender;
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

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserEntity(userRepo.save(newUser));
        tokenRepo.save(verificationToken);
//mail
        SimpleMailMessage confirmMessage = new SimpleMailMessage();
        confirmMessage.setTo(registrationDTO.getEmail());
        confirmMessage.setFrom("citrusgo54@gmail.com");
        confirmMessage.setSubject("Email confirmation");
        confirmMessage.setText("http://localhost:8080/auth/registration/mailconfirmation?token=" + token);
        mailSender.send(confirmMessage);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> enableUser(String token){
        Optional<VerificationToken> verificationToken = tokenRepo.findByToken(token);
        if(!verificationToken.isPresent()){
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        //update users emailConfirmed field
        UserEntity userEntity = verificationToken.get().getUserEntity();
        userEntity.setEmailConfirmed(true);
        userRepo.save(userEntity);
        return new ResponseEntity<>("Email confirmed", HttpStatus.OK);
    }

    public ResponseEntity<String> sign(LoginDTO loginDTO){
        if(!userRepo.existsByEmail(loginDTO.getEmail())){
            return new ResponseEntity<>("Wrong email", HttpStatus.I_AM_A_TEAPOT);//ХЫЫЫЫЫЫЫ
        }
        if(userRepo.findByEmail(loginDTO.getEmail()).getPassword().equals(passwordEncoder.encode(loginDTO.getPassword()))){
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
