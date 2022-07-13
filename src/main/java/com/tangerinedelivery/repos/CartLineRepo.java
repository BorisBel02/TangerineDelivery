package com.tangerinedelivery.repos;

import com.tangerinedelivery.repos.entities.CartEntity;
import com.tangerinedelivery.repos.entities.CartLineEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartLineRepo extends CrudRepository<CartLineEntity, Long> {
    CartLineEntity findByCartAndProductID(CartEntity cart, Long productID);

    void deleteById(Long id);
    void deleteByCartAndProductID(CartEntity cart, Long productID);
}
