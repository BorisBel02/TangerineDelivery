package com.tangerinedelivery.repo;

import com.tangerinedelivery.repo.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartRepo extends CrudRepository<CartEntity, Long> {
    CartEntity findByUserEmail(String email);

    @Override
    <S extends CartEntity> S save(S s);

    @Override
    void delete(CartEntity cartEntity);
}
