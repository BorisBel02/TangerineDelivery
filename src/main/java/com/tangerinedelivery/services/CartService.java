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
        return cartRepo.findeByUser(user);
    }
    public Integer addProduct(Integer productId, Integer quantity, UserEntity user){
        Integer addedQuantity = quantity;
        ProductEntity product = productRepo.findById(productId).get();
        CartLineInfo cartLineInfo = cartRepo.findByUserAndProduct(user,product);
        if(cartLineInfo!=null){
            addedQuantity=cartLineInfo.getQuantity()+quantity;
            cartLineInfo.setQuantity(addedQuantity);
        }
        else{
            cartLineInfo=new CartLineInfo();
            cartLineInfo.setQuantity(quantity);
            cartLineInfo.setProductID(productId);
        }
        cartRepo.save(cartLineInfo);
        return addedQuantity;
    }
    public float updateQuantity(Integer productId, Integer quantity, UserEntity user){
        cartRepo.updateQuantity(quantity,productId,user.getUserID());
        ProductEntity product = productRepo.findById(productId).get();
        float subtotal = product.getPrice()*quantity;
        return subtotal;
    }
    public void removeProduct(Integer productId, UserEntity user){
        cartRepo.deleteByCustomerAndProduct(user.getUserID(),productId);
    }
}
