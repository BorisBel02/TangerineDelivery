package com.tangerinedelivery.controllers;

import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.exception.EmailAlreadyUsedException;
import com.tangerinedelivery.exception.UserNotFoundException;
import com.tangerinedelivery.services.OrderService;

import com.tangerinedelivery.models.UserModel;

import com.tangerinedelivery.services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/cart")
    public ResponseEntity<String> purchase(@RequestParam String address){
        return orderService.purchaseCart(address);
    }
    @GetMapping("/all")
    public List<UserModel> getAll() {
        return userService.findAll().stream().map(UserModel::ToModel).collect(Collectors.toList());
    }


    @GetMapping
    public ResponseEntity getUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(UserModel.ToModel(userService.findUserById(id)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
