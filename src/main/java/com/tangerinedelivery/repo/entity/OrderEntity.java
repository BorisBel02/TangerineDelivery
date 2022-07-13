package com.tangerinedelivery.repo.entity;



import lombok.Data;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Integer price;

    String status;

    Date date;

    String address;

    Long userID;//для поиска всех заказов пользователя




}
