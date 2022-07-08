package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.ProductEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.entities.CartInfo;
import com.tangerinedelivery.entities.CartLineInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartLineInfo, Integer > {
     List<CartLineInfo> findeByUser(UserEntity user);
     CartLineInfo findByUserAndProduct(UserEntity user, ProductEntity product);
     void updateQuantity(Integer quantity, Integer productId, Long customerId);
     public void deleteByCustomerAndProduct(Long customerId, Integer productId);


}
