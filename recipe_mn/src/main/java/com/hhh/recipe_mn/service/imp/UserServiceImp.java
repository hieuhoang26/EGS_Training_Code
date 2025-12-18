package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService  {
    final UserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService() {
        return username -> {

            Optional<User> userOptional = userRepository.findByEmail(username);


            User user = userOptional.orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email: " + username)
            );


            return user;
        };
    }

    @Override
    public Set<String> extractAuthorities(User user) {
//        Set<String> authorities = new HashSet<>();
//        for (Role role : user.getRoles()){
////            roles.add("ROLE_" + role.getName());
//            authorities.add(role.getName());
//            for (Permission permission : role.getPermissions()) {
//                authorities.add(permission.getName());
//            }
//        }
//        return authorities;
        return null;
    }

    @Override
    public User getById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Not Found User"));
    }

    @Override
    public boolean existUser(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
