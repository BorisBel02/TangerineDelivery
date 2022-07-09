package com.tangerinedelivery.entities;

import com.tangerinedelivery.entities.ProductEntity;

import javax.persistence.*;

@Entity
@Table (name = "cartline")
public class CartLineInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer productID;
    private String user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private int quantity;
    public CartLineInfo() {
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID=productID;
    }

}
