package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.RoleService;
import com.example.socialmediaapi.core.entities.Role;
import com.example.socialmediaapi.core.repositories.RoleRepository;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(String roleName) {
       Role role = roleRepository.findByName(roleName);
       if (role == null) throw new ResourceNotFoundException(String.format("Role '%s' not found", roleName));
       return role;
    }
}
