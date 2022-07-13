package com.tangerinedelivery.repos;

import com.tangerinedelivery.repos.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);

    @Override
    <S extends UserEntity> S save(S s);

    public Boolean existsByEmail(String email);

}
