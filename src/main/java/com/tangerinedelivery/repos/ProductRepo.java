//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductRepo extends CrudRepository<ProductEntity, Integer> {
    List<ProductEntity> findAll();

    List<ProductEntity> findByName(String name);

    @Override
    <S extends ProductEntity> S save(S s);
}
