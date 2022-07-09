//created by B.Belyavtsev 07.07.2022
//класс для проверки работоспособности запросов
package com.tangerinedelivery.controllers;

import com.tangerinedelivery.entities.CartInfo;
import com.tangerinedelivery.entities.CartLineInfo;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.exception.UserNotFoundException;
import com.tangerinedelivery.services.CartService;
import org.springframework.ui.Model;
import com.tangerinedelivery.DTOs.LoginDTO;
import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.repos.ProductRepo;
import com.tangerinedelivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class TestController{
    public TestController() {
    }

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;


    @GetMapping("/getRegDTO")
    ResponseEntity<?> getRegDTOs(){
        return new ResponseEntity<>(new RegistrationDTO(), HttpStatus.OK);
    }
    @GetMapping("/getLoginDTO")
    ResponseEntity<?> getLoginDTOs(){
        return new ResponseEntity<>(new LoginDTO(), HttpStatus.OK);
    }
    /*@GetMapping("/")
    List<ProductEntity> getAll(){
        //return productRepo.findAll();
        ArrayList<ProductEntity> list = new ArrayList<ProductEntity>();
        list.add(new ProductEntity("Orange", 500, "Just an orange"));
        return list;
    }*/
    @GetMapping("/shoppingCart")
    public String showShoopingCartView(HttpServletRequest request, Model model) {

        return "shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Integer id,
                                 @RequestParam("quantity") int quantity) {

        cartService.updateQuantity(id,quantity);
        return "redirect:shoppingCart";
    }
    @GetMapping("/removeCartItem/{id}")
    public String removeItem(@RequestParam("id") Integer id, UserEntity user) {
        cartService.removeProduct(id,user);
        return "redirect:/shoppingCart";
    }

//    @GetMapping("/cart/update")
//    public String updateQuantity(Integer productId,Integer quantity, UserEntity user){
//        if(user==null){
//            return "You must login";
//        }
//        float subtotal = cartService.updateQuantity(productId,quantity,user);
//
//        return String.valueOf(subtotal);
//    }
//    @GetMapping("/cart/remove")
//    public String removeProductFromCart(Integer productId,Integer quantity, UserEntity user){
//        if(user==null){
//            return "You must login";
//        }
//        cartService.removeProduct(productId,user);
//
//        return "Remove";
//    }
//    @GetMapping("/product")
//    ProductEntity prod(){
//        return new ProductEntity("Orange", 500, "Just an orange");
//    }
   /* @PostMapping("/")
    ProductEntity post(){
        return productRepo.save(new ProductEntity("Orange", 500, "Just an orange"));
    }
*/


}
