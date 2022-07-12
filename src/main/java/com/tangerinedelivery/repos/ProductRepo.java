//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.repos;

import com.tangerinedelivery.categories.Categories;
import com.tangerinedelivery.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();

    ProductEntity findByName(String name);

    List<ProductEntity> findByNameStartingWith(String name);

    List<ProductEntity> findByCategory(Categories category);

    List<ProductEntity> findByCategoryAndNameStartingWith(Categories category, String name);

    ProductEntity findByProductID(Long productID);


    @Override
    <S extends ProductEntity> S save(S s);


}
