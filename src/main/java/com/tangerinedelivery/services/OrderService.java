package com.tangerinedelivery.services;

import com.tangerinedelivery.entities.CartEntity;
import com.tangerinedelivery.entities.OrderEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.OrderRepo;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(UserRepo userRepo, OrderRepo orderRepo){
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }
    public ResponseEntity<String> purchaseCart(String address){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepo.findByEmail(userDetails.getUsername());
        CartEntity cartEntity = userEntity.getCartEntity();
        cartEntity.setUser(null);

        CartEntity newCart = new CartEntity();
        newCart.setUser(userEntity);

        userEntity.setCartEntity(newCart);
        userRepo.save(userEntity);

        OrderEntity order = new OrderEntity();
        order.setCartEntity(cartEntity);
        order.setAddress(address);
        order.setDate(new Date());
        order.setUserID(userEntity.getUserID());
        order.setStatus("Formed");

        orderRepo.save(order);
        return new ResponseEntity<String>("Order successfully formed", HttpStatus.OK);
    }
}
