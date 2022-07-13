package com.tangerinedelivery.repos;

import com.tangerinedelivery.repos.entities.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartRepo extends CrudRepository<CartEntity, Long> {
    CartEntity findByUserEmail(String email);

    @Override
    <S extends CartEntity> S save(S s);

    @Override
    void delete(CartEntity cartEntity);
}
