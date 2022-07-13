package com.tangerinedelivery.repo;

import com.tangerinedelivery.repo.entity.CartEntity;
import com.tangerinedelivery.repo.entity.CartLineEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartLineRepo extends CrudRepository<CartLineEntity, Long> {
    CartLineEntity findByCartAndProductID(CartEntity cart, Long productID);

    void deleteById(Long id);
    void deleteByCartAndProductID(CartEntity cart, Long productID);
}
