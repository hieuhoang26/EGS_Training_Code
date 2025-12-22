package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.UserRequest;
import com.hhh.recipe_mn.dto.response.UserResponse;
import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.mapper.UserMapper;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.RoleRepository;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    @Lazy
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;


    @Override
    public UserDetailsService userDetailsService() {
        return username -> {

            Optional<User> userOptional = userRepository.findByEmail(username);


            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));


            return user;
        };
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

    @Override
    public User assignRoles(UUID userId, Set<UUID> roleIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setStatus(UserStatus.ACTIVE);

        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(
                    roleRepository.findAllById(request.getRoleIds())
            );
            user.setRoles(roles);
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getDetailById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setStatus(request.getStatus());

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(
                    roleRepository.findAllById(request.getRoleIds())
            );
            user.setRoles(roles);
        }

        return userMapper.toResponse(user);
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }


}
