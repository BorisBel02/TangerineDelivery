//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.controllers;
//контроллер на "пусой запрос" - http://tangerinedelivery
//вывод основной страницы (со всеми продуктами например)

import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.repos.ProductRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class MainController {

    private final ProductRepo productRepo;

    @Autowired
    public MainController(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @ApiOperation("Get all products")
    @GetMapping("/")
    List<ProductEntity> getAll(){
            return productRepo.findAll();
    }

    @ApiOperation("Search by name")
    @GetMapping("/search")
    List<ProductEntity> getProductByName(@RequestParam String name){
        return productRepo.findByName(name);
    }



}
