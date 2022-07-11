package com.tangerinedelivery.controllers;

import com.tangerinedelivery.exception.ProductNotFoundException;
import com.tangerinedelivery.services.CartService;
import com.tangerinedelivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

//    @PostMapping(value = "/add", params = {"name", "quantity"})
//    public ResponseEntity addProduct(@RequestParam(value = "name") String name, @RequestParam Long quantity) {
//        try {
//            cartService.addProduct(name, quantity);
//            return ResponseEntity.ok("Product added");
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping(value = "/add", params = {"id", "quantity"})
    public ResponseEntity addProduct(@RequestParam(value = "id") Long id, @RequestParam Long quantity) {
        try {
            cartService.addProduct(id, quantity);
            return ResponseEntity.ok("Product added");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping(value = "/delete", params = {"name", "quantity"})
//    public ResponseEntity deleteProduct(@RequestParam(value = "name") String name, @RequestParam Long quantity) {
//        try {
//            if (quantity < 0)
//                cartService.addProduct(name, quantity);
//            else
//                cartService.addProduct(name, -quantity);
//            return ResponseEntity.ok("Product deleted");
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping(value = "/delete", params = {"id", "quantity"})
    public ResponseEntity deleteProduct(@RequestParam(value = "id") Long id, @RequestParam Long quantity) {
        try {
            if (quantity < 0)
                cartService.addProduct(id, quantity);
            else
                cartService.addProduct(id, -quantity);
            return ResponseEntity.ok("Product deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
