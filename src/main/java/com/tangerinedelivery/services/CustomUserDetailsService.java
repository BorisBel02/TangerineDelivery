package com.tangerinedelivery.services;

import com.tangerinedelivery.entities.RoleEntity;
import com.tangerinedelivery.entities.UserEntity;
import com.tangerinedelivery.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        Optional<UserEntity> user = Optional.of(userRepository.findByEmail(Email));
        if(user.isPresent()){
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
        }
        else {
            throw  new UsernameNotFoundException("User not found with email:" + Email);
        }
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<RoleEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
