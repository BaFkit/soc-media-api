package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.configs.AppConfig;
import com.example.socialmediaapi.contracts.RoleService;
import com.example.socialmediaapi.converters.mappers.EntityDtoMapper;
import com.example.socialmediaapi.core.entities.User;
import com.example.socialmediaapi.core.repositories.UserRepository;
import com.example.socialmediaapi.dto.requests.SignupRequest;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import com.example.socialmediaapi.exceptions.ResourceExistsException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@SpringBootTest(classes = {UserServiceImpl.class, PasswordEncoder.class, EntityDtoMapper.class, AppConfig.class})
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleService roleService;

    private User userTest1;
    private Optional<User> userTest2;

    @BeforeEach
    void setUp() {
        userTest1 = new User();
        userTest1.setId(UUID.randomUUID());
        userTest1.setRoles(new ArrayList<>());
        userTest1.setUsername("userTest1");
        userTest1.setEmail("userTest1@email.com");
        userTest1.setPassword("100");

        userTest2 = Optional.of(new User());
        userTest2.get().setId(UUID.randomUUID());
        userTest2.get().setRoles(new ArrayList<>());
        userTest2.get().setUsername("userTest2");
        userTest2.get().setEmail("userTest2@email.com");
        userTest2.get().setPassword("100");
    }

    @Test
    void loadUserByUsername() {
        Mockito.doReturn(userTest1).when(userRepository).findByUsername("userTest1");
        Mockito.doReturn(userTest1).when(userRepository).findByEmail("userTest1@email.com");

        UserDetails receivedUser = userService.loadUserByUsername("userTest1");
        Assertions.assertEquals(receivedUser.getUsername(), userTest1.getUsername());

        receivedUser = userService.loadUserByUsername("userTest1@email.com");
        Assertions.assertEquals(receivedUser.getUsername(), userTest1.getUsername());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.loadUserByUsername("usertest"));
        Assertions.assertEquals("User 'usertest' not found", exception.getMessage());
        exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.loadUserByUsername("userTest1@email.ru"));
        Assertions.assertEquals("User with email: 'userTest1@email.ru' not found", exception.getMessage());
    }

    @Test
    void findUserById() {
        Mockito.doReturn(userTest2).when(userRepository).findById(userTest2.get().getId());
        User receivedUser = userService.findUserById(userTest2.get().getId());
        Assertions.assertEquals(receivedUser.getUsername(), userTest2.get().getUsername());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(UUID.randomUUID()));
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    void createUser() {
        SignupRequest newUserTest = new SignupRequest();
        newUserTest.setUsername("newUserTest");
        newUserTest.setEmail("newUserTest@email.com");
        newUserTest.setPassword("100");
        newUserTest.setConfirmPassword("100");

        Mockito.doReturn(true).when(userRepository).existsUserByUsername("newUserTest");
        ResourceExistsException exception1 = Assertions.assertThrows(ResourceExistsException.class, () -> userService.createUser(newUserTest));
        Assertions.assertEquals("User 'newUserTest' already exists", exception1.getMessage());
        Mockito.doReturn(false).when(userRepository).existsUserByUsername("newUserTest");
        Mockito.doReturn(true).when(userRepository).existsUserByEmail("newUserTest@email.com");
        ResourceExistsException exception2 = Assertions.assertThrows(ResourceExistsException.class, () -> userService.createUser(newUserTest));
        Assertions.assertEquals("User with email: 'newUserTest@email.com' already exists", exception2.getMessage());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(a -> a.getArguments()[0]);

    }

    @Test
    void checkUserExist() {
        Mockito.doReturn(false).when(userRepository).existsById(userTest1.getId());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.checkUserExist(userTest1.getId()));
        Assertions.assertEquals("User '" + userTest1.getId() + "' not found", exception.getMessage());


    }

    @Test
    void findAllUser() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(userTest1);
        testUsers.add(userTest2.get());
        Mockito.doReturn(testUsers).when(userRepository).findAll();
        List<UserDtoOut> testUsersDtos = testUsers.stream().map(EntityDtoMapper.INSTANCE::toUserDtoOut).collect(Collectors.toList());
        List<UserDtoOut> userDtoOutsTest = userService.findAllUser();
        Assertions.assertEquals(userDtoOutsTest, testUsersDtos);
    }
}