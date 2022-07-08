package com.tangerinedelivery.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;

@Entity
@Table(name = "carts")
public class CartInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

    public CartInfo() {
    }
    public CartInfo(int ProductID, int Quantity) {
        CartLineInfo cart = null;
        cart.setProductID(ProductID);
        cart.setQuantity(Quantity);
        cartLines.add(cart);
    }
    public List<CartLineInfo> getCartLines() {
        return cartLines;
    }

    public Long getCartId() {
        return CartId;
    }

    public void setCartId(Long cartId) {
        CartId = cartId;
    }
}
