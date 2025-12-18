package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.repository.RoleRepository;
import com.hhh.recipe_mn.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    final RoleRepository roleRepository;
    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
