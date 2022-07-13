package com.tangerinedelivery.models;

import com.tangerinedelivery.repos.entities.CartLineEntity;

public class CartLineModel {

    private Long productID;
    private Long quantity;

    public CartLineModel() {
    }

    public static CartLineModel toModel(CartLineEntity cartLineEntity)
    {
        CartLineModel cartLineModel = new CartLineModel();
        cartLineModel.setProductID(cartLineEntity.getProductID());
        cartLineModel.setQuantity(cartLineEntity.getQuantity());

        return cartLineModel;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
