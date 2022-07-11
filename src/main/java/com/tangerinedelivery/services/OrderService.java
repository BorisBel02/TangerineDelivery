package com.tangerinedelivery.services;

import com.tangerinedelivery.entities.CartEntity;
import com.tangerinedelivery.entities.OrderEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.OrderRepo;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final JavaMailSender mailSender;

    @Autowired
    public OrderService(UserRepo userRepo, OrderRepo orderRepo, JavaMailSender mailSender){
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.mailSender = mailSender;
    }
    public ResponseEntity<String> purchaseCart(String address){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepo.findByEmail(userDetails.getUsername());
        if(!userEntity.getEmailConfirmed()){
            return new ResponseEntity<>("Your email must be confirmed", HttpStatus.BAD_REQUEST);
        }
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
        SimpleMailMessage confirmMessage = new SimpleMailMessage();
        confirmMessage.setTo(userDetails.getUsername());
        confirmMessage.setFrom("citrusgo54@gmail.com");
        confirmMessage.setSubject("Your order");
        confirmMessage.setText(order.toString());
        mailSender.send(confirmMessage);
        return new ResponseEntity<>("Order successfully formed", HttpStatus.OK);
    }

    public List<OrderEntity> getUserOrders(Long id){
        return orderRepo.findAllByUserID(id);
    }
}
