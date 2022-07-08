package com.tangerinedelivery.entities;

import com.tangerinedelivery.entities.ProductEntity;

import javax.persistence.*;

@Entity
@Table (name = "cartline")
public class CartLineInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long productID;
    private int quantity;
    public CartLineInfo() {
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Long getProductID() {
        return productID;
    }

}
