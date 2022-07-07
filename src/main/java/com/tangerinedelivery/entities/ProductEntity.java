//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productID;

    private String name;

    private int price;

    private String description;

    public ProductEntity() {
    }



    public ProductEntity(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
