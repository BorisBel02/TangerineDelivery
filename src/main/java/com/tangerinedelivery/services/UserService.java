package com.tangerinedelivery.services;

import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.exception.EmailAlreadyUsedException;
import com.tangerinedelivery.exception.UserNotFoundException;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity findUserById(Long id) throws UserNotFoundException {
        UserEntity userEntity = userRepo.findById(id).get();
        if (userEntity == null) {
            throw new UserNotFoundException("User not found");
        }
        return userEntity;
    }

    public List<UserEntity> findAll()
    {
        Test();
        return (List<UserEntity>) userRepo.findAll();
    }

    public UserEntity addUser(UserEntity user) throws EmailAlreadyUsedException {
        if (userRepo.findUserEntityByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyUsedException("Email already used");
        }
        return userRepo.save(user);
    }

    public void Test()
    {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = ((UserDetails)obj);
        System.out.println();
    }

}
