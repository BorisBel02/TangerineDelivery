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
    @Column(name = "user", nullable = false, unique = true)
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCartLines(List<CartLineInfo> cartLines) {
        this.cartLines = cartLines;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

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
