//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();

    List<ProductEntity> findByNameStartingWith(String name);

    ProductEntity findByProductID(Long productID);

    @Override
    <S extends ProductEntity> S save(S s);


}
