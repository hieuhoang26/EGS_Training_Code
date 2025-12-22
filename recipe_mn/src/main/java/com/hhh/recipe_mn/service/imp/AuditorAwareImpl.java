package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;




@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    private final UserRepository userRepository;
    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.of("system");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User  user = userRepository.findByEmail(email).orElse(null);
        return Optional.of(user.getEmail());
    }
}
