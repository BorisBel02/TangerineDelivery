package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.entities.CartLineInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartLineInfo, Integer > {
     List<CartLineInfo> findByUser(UserEntity user);
     CartLineInfo findByUserAndProductID(UserEntity user, Integer productID);
     void Quantity(Integer quantity);
     public void deleteByUserAndProductID(Long userId, Integer productID);


}
