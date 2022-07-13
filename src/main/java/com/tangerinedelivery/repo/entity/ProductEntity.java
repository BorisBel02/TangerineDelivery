//created by B.Belyavtsev 06.07.2022

package com.tangerinedelivery.repo.entity;


import com.tangerinedelivery.enums.Categories;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private short discount;

    private Boolean popular;

    private int price;

    private String description;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "product", orphanRemoval = true)
        private List<CommentEntity> comments = new ArrayList<>();
    public ProductEntity() {
        popular = false;
        discount = 0;
    }



    public ProductEntity(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        popular = false;
        discount = 0;
    }

}
