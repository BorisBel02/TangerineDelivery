package com.tangerinedelivery.repo;



import com.tangerinedelivery.repo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    @Override
    <S extends RoleEntity> S save(S s);
}