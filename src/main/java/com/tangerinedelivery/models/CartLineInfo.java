package com.tangerinedelivery.models;

import com.tangerinedelivery.entities.ProductEntity;

public class CartLineInfo {
    private Long productID;
    private int quantity;
    public CartLineInfo() {
        this.quantity = 0;
    }

    public int getQuantity() {
        return quantity;
    }
    public Long getProductID() {
        return productID;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
