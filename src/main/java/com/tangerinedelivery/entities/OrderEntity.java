package com.tangerinedelivery.entities;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Integer price;

    String status;

    Date date;

    String address;

    Long userID;//id для поиска всех заказов пользователя

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private CartEntity cartEntity;


}
