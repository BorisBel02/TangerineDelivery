package com.tangerinedelivery.repos;

import com.tangerinedelivery.repos.entities.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepo extends CrudRepository<VerificationToken, Long> {
    @Override
    <S extends VerificationToken> S save(S s);

    @Override
    void deleteById(Long aLong);

    Optional<VerificationToken> findByToken(String token);
}
