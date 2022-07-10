package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.CartEntity;
import com.tangerinedelivery.entities.CartLineEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartLineRepo extends CrudRepository<CartLineEntity, Long> {
    CartLineEntity findByCartAndAndProductID(CartEntity cart, Long productID);
    void deleteByCartAndProductID(CartEntity cart, Long productID);
}
