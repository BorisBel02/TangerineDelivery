//created by B.Belyavtsev 07.07.2022
//класс для проверки работоспособности запросов
package com.tangerinedelivery.controllers;


import com.tangerinedelivery.DTOs.LoginDTO;
import com.tangerinedelivery.DTOs.RegistrationDTO;
import com.tangerinedelivery.entities.CartInfo;
import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController{
    public TestController() {
    }

    @Autowired
    private ProductRepo productRepo;


    @GetMapping("/getRegDTOs")
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
    @GetMapping("/cart")
    CartInfo cart(){return new CartInfo(1,14);}
    @GetMapping("/product")
    ProductEntity prod(){
        return new ProductEntity("Orange", 500, "Just an orange");
    }
   /* @PostMapping("/")
    ProductEntity post(){
        return productRepo.save(new ProductEntity("Orange", 500, "Just an orange"));
    }
*/


}
