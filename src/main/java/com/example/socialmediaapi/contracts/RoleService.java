package com.example.socialmediaapi.contracts;

import com.example.socialmediaapi.core.entities.Role;

public interface RoleService {

    Role findRoleByName(String roleName);

}
