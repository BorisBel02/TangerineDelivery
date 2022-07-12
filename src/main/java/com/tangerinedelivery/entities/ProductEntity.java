//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.entities;

import com.tangerinedelivery.categories.Categories;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productID;

    private String name;

    private Categories category;

    private int discount;

    private Boolean popular;

    private int price;

    private String description;

    public ProductEntity() {
        popular = false;
        discount = 0;
    }



    public ProductEntity(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
