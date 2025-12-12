package org.example.search_advance.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.search_advance.model.UserHasRole;
import org.example.search_advance.repository.UserHasRoleRepository;
import org.example.search_advance.service.UserHasRoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHasRoleServiceImp implements UserHasRoleService {
    final UserHasRoleRepository userHasRoleRepository;
    @Override
    public void save(UserHasRole userHasRole) {
        userHasRoleRepository.save(userHasRole);
    }
}
