//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.controllers;
//контроллер на "пусой запрос" - http://tangerinedelivery
//вывод основной страницы (со всеми продуктами например)

import com.tangerinedelivery.enums.Categories;
import com.tangerinedelivery.repos.entities.ProductEntity;
import com.tangerinedelivery.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController {

    private final ProductRepo productRepo;

    @Autowired
    public MainController(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @GetMapping("/")
    List<ProductEntity> getAll(){
            return productRepo.findAll();
    }

    @GetMapping("/search")
    List<ProductEntity> getProductByName(@RequestParam String name){
        return productRepo.findByNameStartingWith(name);
    }

    @GetMapping("/{category}")
    public List<ProductEntity> getProductsFromCategory(@PathVariable("category") Categories category){
        return productRepo.findByCategory(category);
    }

    @GetMapping("/{category}/search")
    public List<ProductEntity> getProductsByNameFromCategory(@PathVariable("category") Categories category, @RequestParam String name){
        return productRepo.findByCategoryAndNameStartingWith(category, name);
    }


}
