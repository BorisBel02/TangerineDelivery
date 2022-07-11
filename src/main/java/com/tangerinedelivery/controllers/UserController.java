package com.tangerinedelivery.controllers;

import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.exception.EmailAlreadyUsedException;
import com.tangerinedelivery.exception.UserNotFoundException;
import com.tangerinedelivery.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public List<UserEntity> getAll() {
        return userService.findAll();
    }


    @GetMapping
    public ResponseEntity getUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.findUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
