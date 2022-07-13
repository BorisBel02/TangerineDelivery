package com.tangerinedelivery.service;


import com.tangerinedelivery.repo.CartRepo;
import com.tangerinedelivery.repo.OrderRepo;
import com.tangerinedelivery.repo.ProductRepo;
import com.tangerinedelivery.repo.UserRepo;
import com.tangerinedelivery.repo.entity.*;
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
    private final CartRepo cartRepo;
    
    private final ProductRepo productRepo;
    private final JavaMailSender mailSender;

    @Autowired
    public OrderService(UserRepo userRepo, OrderRepo orderRepo, JavaMailSender mailSender, CartRepo cartRepo, ProductRepo productRepo){
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.mailSender = mailSender;
        this.productRepo = productRepo;
    }
    public ResponseEntity<String> purchaseCart(String address){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepo.findByEmail(userDetails.getUsername());
        if(!userEntity.getEmailConfirmed()){
            return new ResponseEntity<>("Your email must be confirmed", HttpStatus.BAD_REQUEST);
        }
        CartEntity cartEntity = userEntity.getCartEntity();
        //cartEntity.setUser(null); // очень страшное место
        List<CartLineEntity> products = cartEntity.getCartLines();
        if(products.isEmpty()){
            return new ResponseEntity<>("You can not purchase empty cart", HttpStatus.BAD_REQUEST);
        }
        CartEntity newCart = new CartEntity();
        newCart.setUser(userEntity);

        userEntity.setCartEntity(newCart);

        cartRepo.save(newCart);
        userRepo.save(userEntity);

        OrderEntity order = new OrderEntity();
        order.setAddress(address);
        order.setDate(new Date());
        order.setUserID(userEntity.getUserID());
        order.setStatus("Formed");
        String message = "";
        Long totalPrice = 0L;

        for(CartLineEntity e : products){
            ProductEntity productEntity = productRepo.findByProductID(e.getProductID());
            long price = e.getQuantity() * productEntity.getPrice();
            message += productEntity.getName() + " - " + e.getQuantity() + ", price: " + price + '\n';
            totalPrice += price;
        }
        message += "\nTotal price: " + totalPrice.toString();
        orderRepo.save(order);
        cartRepo.delete(cartEntity);
        SimpleMailMessage orderMessage = new SimpleMailMessage();
        orderMessage.setTo(userDetails.getUsername());
        orderMessage.setFrom("citrusgo54@gmail.com");
        orderMessage.setSubject("Your order from: " + order.getDate().toString());
        orderMessage.setText(message);
        mailSender.send(orderMessage);
        return new ResponseEntity<>("Order successfully formed", HttpStatus.OK);
    }

    public List<OrderEntity> getUserOrders(Long id){
        return orderRepo.findAllByUserID(id);
    }
}
