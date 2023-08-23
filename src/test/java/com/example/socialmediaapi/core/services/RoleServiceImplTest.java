package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.configs.AppConfig;
import com.example.socialmediaapi.core.entities.Role;
import com.example.socialmediaapi.core.repositories.RoleRepository;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {RoleServiceImpl.class, AppConfig.class})
class RoleServiceImplTest {

    @Autowired
    private RoleServiceImpl roleService;

    @MockBean
    private RoleRepository roleRepository;

    private Role roleTest;

    @BeforeEach
    void setUp() {
        roleTest = new Role();
        roleTest.setId(1L);
        roleTest.setName("ROLE_TEST");
        roleTest.setCreatedAt(LocalDateTime.now());
        roleTest.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void findRoleByName() {

        Mockito.doReturn(roleTest).when(roleRepository).findByName("ROLE_TEST");
        Role receivedRole = roleService.findRoleByName("ROLE_TEST");
        Assertions.assertEquals(receivedRole, roleTest);

        Mockito.doReturn(null).when(roleRepository).findByName("ROLE_TEST1");
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> roleService.findRoleByName("ROLE_TEST1"));
        Assertions.assertEquals("Role 'ROLE_TEST1' not found" , exception.getMessage());

    }
}