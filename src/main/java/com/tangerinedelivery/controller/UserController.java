package com.tangerinedelivery.controller;

import com.tangerinedelivery.repo.entity.OrderEntity;
import com.tangerinedelivery.repo.entity.UserEntity;
import com.tangerinedelivery.repo.UserRepo;
import com.tangerinedelivery.service.OrderService;

import com.tangerinedelivery.model.UserModel;

import com.tangerinedelivery.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserRepo userRepo, UserService userService, OrderService orderService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public UserModel usercab(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepo.findByEmail(userDetails.getUsername());
        return UserModel.ToModel(userEntity);
    }

    @GetMapping("/cart/purchase")
    public ResponseEntity<String> purchase(@RequestParam String address, @RequestParam String details){
        return orderService.purchaseCart(address,details);
    }
    @GetMapping("ordershistory")
    public List<OrderEntity> orders(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepo.findByEmail(userDetails.getUsername());
        return orderService.getUserOrders(userEntity.getUserID());
    }



    @GetMapping("/all")
    public List<UserModel> getAll() {
        return userService.findAll().stream().map(UserModel::ToModel).collect(Collectors.toList());
    }


    /*@GetMapping
    public ResponseEntity getUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(ToModel(userService.findUserById(id)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    //new method with /user/cart mapping



}
