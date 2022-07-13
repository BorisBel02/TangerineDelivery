//created by B.Belyavtsev 06.07.2022
package com.tangerinedelivery.repo;

import com.tangerinedelivery.enums.Categories;
import com.tangerinedelivery.repo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select p from ProductEntity p where p.discount > 0")
    List<ProductEntity> findDiscounted();

    @Query("select p from ProductEntity p where p.popular = true")
    List<ProductEntity> findPopular();

    @Query("select p from ProductEntity p where p.discount > 1 and p.popular = true")
    List<ProductEntity> findPopularAndDiscounted();

    @Query("select p from ProductEntity p where p.discount > 0 and p.name like ?1")
    List<ProductEntity> findDiscountedByNameStartingWith(String name);

    @Query("select p from ProductEntity p where p.popular = true and p.name like ?1")
    List<ProductEntity> findPopularByNameStartingWith(String name);

    @Query("select p from ProductEntity p where p.discount > 1 and p.popular = true")
    List<ProductEntity> findPopularAndDiscountedByNameStartingWith(String name);
    @Override
    <S extends ProductEntity> S save(S s);


}
