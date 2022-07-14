package com.tangerinedelivery.service;

import com.tangerinedelivery.repo.CartRepo;
import com.tangerinedelivery.repo.RoleRepo;
import com.tangerinedelivery.repo.UserRepo;
import com.tangerinedelivery.repo.entity.CartEntity;
import com.tangerinedelivery.repo.entity.RoleEntity;
import com.tangerinedelivery.repo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private CartRepo cartRepo;


    @Autowired
    public DataLoader(UserRepo userRepo, RoleRepo roleRepo, CartRepo cartRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.cartRepo = cartRepo;
    }

    @PostConstruct
    public void loadData() {
        Optional<RoleEntity> role = roleRepo.findByName("admin");
        if(role.isPresent() == false)
        {
            RoleEntity newRole = new RoleEntity();
            newRole.setName("admin");
            roleRepo.save(newRole);
        }
        if(userRepo.existsByEmail("citrusgo54@gmail.com") == false)
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            UserEntity newUser = new UserEntity();

            newUser.setEmail("citrusgo54@gmail.com");
            newUser.setFirstName("Admin");
            newUser.setMiddleName("Admin");
            newUser.setLastName("Admin");
            newUser.setPassword(encoder.encode("password"));
            newUser.setEmailConfirmed(true);

            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepo.findByName("admin").get());
            newUser.setRoles(roles);

            CartEntity cartEntity = new CartEntity();

            newUser.setCartEntity(cartEntity);

            cartEntity.setUser(newUser);
            cartRepo.save(cartEntity);
        }
    }
}
