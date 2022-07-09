package com.tangerinedelivery.services;
import com.tangerinedelivery.entities.CartLineInfo;
import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.CartRepo;
import com.tangerinedelivery.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CartService {
    @Autowired
    private static CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;
    public static List<CartLineInfo> listCart(UserEntity user){
        return cartRepo.findByUser(user);
    }
    public Integer addProduct(Integer productID, Integer quantity, UserEntity user){
        Integer addedQuantity = quantity;
        CartLineInfo cartLineInfo = cartRepo.findByUserAndProductID(user,productID);
        if(cartLineInfo!=null){
            addedQuantity=cartLineInfo.getQuantity()+quantity;
            cartLineInfo.setQuantity(addedQuantity);
        }
        else{
            cartLineInfo=new CartLineInfo();
            cartLineInfo.setQuantity(quantity);
            cartLineInfo.setProductID(productID);
        }
        cartRepo.save(cartLineInfo);
        return addedQuantity;
    }
    public float updateQuantity(Integer productID, Integer quantity, UserEntity user){
        cartRepo.Quantity(quantity);
        ProductEntity product = productRepo.findById(productID).get();
        float subtotal = product.getPrice()*quantity;
        return subtotal;
    }
    public void removeProduct(Integer productID, UserEntity user){
        cartRepo.deleteByUserAndProductID(user.getUserID(),productID);
    }
}
