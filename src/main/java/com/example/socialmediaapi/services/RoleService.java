package com.example.socialmediaapi.services;

import com.example.socialmediaapi.entities.Role;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findRoleByName(String roleName) {
       Role role = roleRepository.findByName(roleName);
       if (role == null) throw new ResourceNotFoundException(String.format("Role '%s' not found", roleName));
       return role;
    }

}
