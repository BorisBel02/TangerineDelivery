package com.tangerinedelivery.services;

import com.tangerinedelivery.entities.CartEntity;
import com.tangerinedelivery.entities.CartLineEntity;
import com.tangerinedelivery.exception.ProductNotFoundException;
import com.tangerinedelivery.repos.CartLineRepo;
import com.tangerinedelivery.repos.CartRepo;
import com.tangerinedelivery.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepo cartRepo;
    private CartLineRepo cartLineRepo;
    private ProductRepo productRepo;

    @Autowired
    public CartService(CartRepo cartRepo, CartLineRepo cartLineRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.cartLineRepo = cartLineRepo;
        this.productRepo = productRepo;
    }

    public void addProduct(Long productID, Long quantity) throws ProductNotFoundException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (productRepo.findByProductID(productID) == null) {
            throw new ProductNotFoundException("Product not found");
        }
        CartLineEntity cartLine = cartLineRepo.findByCartAndAndProductID(cartRepo.findByUserEmail(userDetails.getUsername()), productID);
        if (cartLine == null) {
            if(quantity < 0)
            {
                throw new ProductNotFoundException("No such product in this cart");
            }
            else {
                cartLine = new CartLineEntity();
                cartLine.setProductID(productID);
                cartLine.setQuantity(quantity);
                cartLine.setCart(cartRepo.findByUserEmail(userDetails.getUsername()));
                cartLineRepo.save(cartLine);
            }

        } else {
            cartLine.setQuantity(cartLine.getQuantity() + quantity);
            if (cartLine.getQuantity() <= 0) {
                removeProduct(productID);
                return;
            }
            cartLineRepo.save(cartLine);
        }

    }

    public void addProduct(String name, Long quantity) throws ProductNotFoundException {
        addProduct(productRepo.findByName(name).getProductID(), quantity);
    }

    public void removeProduct(Long productID) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartLineRepo.deleteByCartAndProductID(cartRepo.findByUserEmail(userDetails.getUsername()), productID);
    }

}
