package com.tangerinedelivery.service;

import com.tangerinedelivery.repo.entity.CartEntity;
import com.tangerinedelivery.repo.entity.CartLineEntity;
import com.tangerinedelivery.exception.ProductNotFoundException;
import com.tangerinedelivery.repo.CartLineRepo;
import com.tangerinedelivery.repo.CartRepo;
import com.tangerinedelivery.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepo cartRepo;
    private final CartLineRepo cartLineRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CartService(CartRepo cartRepo, CartLineRepo cartLineRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.cartLineRepo = cartLineRepo;
        this.productRepo = productRepo;
    }

    public CartEntity getCartByEmail(String email)
    {
        return cartRepo.findByUserEmail(email);
    }

    public void addProduct(Long productID, Long quantity) throws ProductNotFoundException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartEntity cartEntity = cartRepo.findByUserEmail(userDetails.getUsername());

        if (productRepo.findByProductID(productID) == null) {
            throw new ProductNotFoundException("Product not found");
        }
        CartLineEntity cartLine = cartLineRepo.findByCartAndProductID(cartEntity, productID);
        if (cartLine == null) {
            if(quantity < 0)
            {
                throw new ProductNotFoundException("No such product in this cart");
            }
            else {
                cartLine = new CartLineEntity();
                cartLine.setProductID(productID);
                cartLine.setQuantity(quantity);
                cartLine.setCart(cartEntity);
                cartLineRepo.save(cartLine);
            }

        } else {
            cartLine.setQuantity(cartLine.getQuantity() + quantity);
            if (cartLine.getQuantity() <= 0) {
                removeProduct(cartLine.getId());
                return;
            }
            cartLineRepo.save(cartLine);
        }

    }

    public void addProduct(String name, Long quantity) throws ProductNotFoundException {
        addProduct(productRepo.findByName(name).getProductID(), quantity);
    }

    public void removeProduct(Long productID) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartLineRepo.deleteById(productID);

    }

}
