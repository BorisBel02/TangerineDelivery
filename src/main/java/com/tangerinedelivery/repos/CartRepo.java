package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartRepo extends CrudRepository<CartEntity, Long> {
    CartEntity findByUserEmail(String email);

}
