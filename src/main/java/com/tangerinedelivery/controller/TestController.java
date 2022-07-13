//created by B.Belyavtsev 07.07.2022
//класс для проверки работоспособности запросов
package com.tangerinedelivery.controller;


import com.tangerinedelivery.controller.dto.LoginDTO;
import com.tangerinedelivery.controller.dto.RegistrationDTO;
import com.tangerinedelivery.repo.entity.ProductEntity;
import com.tangerinedelivery.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController{
    public TestController() {
    }

    @Autowired
    private ProductRepo productRepo;


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
    @GetMapping("/product")
    ProductEntity prod(){
        return new ProductEntity("Orange", 500, "Just an orange");
    }
    @PostMapping("/product/add")
    ProductEntity post(@RequestBody ProductEntity product){
        return productRepo.save(product);
    }

}
