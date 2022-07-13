package com.tangerinedelivery.service;

import com.tangerinedelivery.controller.dto.LoginDTO;
import com.tangerinedelivery.controller.dto.RegistrationDTO;
import com.tangerinedelivery.repo.entity.CartEntity;
import com.tangerinedelivery.repo.entity.UserEntity;
import com.tangerinedelivery.repo.entity.VerificationToken;
import com.tangerinedelivery.repo.CartRepo;
import com.tangerinedelivery.repo.TokenRepo;
import com.tangerinedelivery.repo.UserRepo;
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
    private final CartRepo cartRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JavaMailSender mailSender;

    @Autowired
    public AuthService(UserRepo userRepo, CartRepo cartRepo, TokenRepo tokenRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JavaMailSender mailSender){
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
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
        if(registrationDTO.getFirstName().isEmpty() || registrationDTO.getLastName().isEmpty()){
            return new ResponseEntity<>("You must fill name fields", HttpStatus.BAD_REQUEST);
        }

        UserEntity newUser = new UserEntity();

        newUser.setEmail(registrationDTO.getEmail());
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setMiddleName(registrationDTO.getMiddleName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        CartEntity cartEntity = new CartEntity();

        newUser.setCartEntity(cartEntity);

        cartEntity.setUser(newUser);
        cartRepo.save(cartEntity);

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
        if(userEntity == null){
            return new ResponseEntity<>("Wrong token", HttpStatus.BAD_REQUEST);
        }
        userEntity.setEmailConfirmed(true);
        tokenRepo.delete(verificationToken.get());
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
