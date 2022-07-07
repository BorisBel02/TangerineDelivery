package com.tangerinedelivery.repos;

import com.tangerinedelivery.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    public UserEntity findUserEntityByEmail(String email);
    public Boolean existsByEmail(String email);
}
