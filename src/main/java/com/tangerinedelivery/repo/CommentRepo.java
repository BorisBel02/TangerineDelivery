package com.tangerinedelivery.repo;

import com.tangerinedelivery.repo.entity.CartLineEntity;
import com.tangerinedelivery.repo.entity.CommentEntity;
import com.tangerinedelivery.repo.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends CrudRepository<CommentEntity, Long> {
    @Override
    void delete(CommentEntity commentEntity);
    @Override
    <S extends CommentEntity> S save(S s);
    List<CommentEntity> findAllByProduct(ProductEntity product);
    Optional<CommentEntity> findById(Long commentId);

}
