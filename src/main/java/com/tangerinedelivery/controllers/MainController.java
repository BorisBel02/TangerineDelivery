//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.controllers;
//контроллер на "пусой запрос" - http://tangerinedelivery
//вывод основной страницы (со всеми продуктами например)

import com.tangerinedelivery.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private ProductRepo productRepo;


}
