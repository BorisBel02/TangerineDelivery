package com.tangerinedelivery.repos;

import com.tangerinedelivery.repos.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    @Override
    <S extends OrderEntity> S save(S s);


    List<OrderEntity> findAllByUserID(Long userID);
}
