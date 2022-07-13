
package com.tangerinedelivery.repo.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.tangerinedelivery.repo.entity.CartLineEntity;
@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cart")
//    @JoinTable(name = "cart",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<CartLineEntity> cartLines = new ArrayList<CartLineEntity>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;



    public CartEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartLineEntity> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<CartLineEntity> cartLines) {
        this.cartLines = cartLines;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
