package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.RoleService;
import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.core.entities.Role;
import com.example.socialmediaapi.core.entities.User;
import com.example.socialmediaapi.core.repositories.UserRepository;
import com.example.socialmediaapi.dto.requests.SignupRequest;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import com.example.socialmediaapi.exceptions.ResourceExistsException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.converters.mappers.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = login.contains("@") ? findUserByEmail(login) : findUserByUsername(login);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new ResourceNotFoundException(String.format("User with email: '%s' not found", email));
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ResourceNotFoundException(String.format("User '%s' not found", username));
        return user;
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void createUser(SignupRequest userIn) {
        User user = new User();
        if (Boolean.TRUE.equals(userRepository.existsUserByUsername(userIn.getUsername()))) throw new ResourceExistsException(String.format("User '%s' already exists", userIn.getUsername()));
        if (Boolean.TRUE.equals(userRepository.existsUserByEmail(userIn.getEmail()))) throw new ResourceExistsException(String.format("User with email: '%s' already exists", userIn.getEmail()));
        user.setUsername(userIn.getUsername());
        user.setEmail(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleService.findRoleByName("ROLE_USER"));
        user.setRoles(userRoles);
        log.info("Saving User {}", userIn.getUsername());
        userRepository.save(user);
    }

    @Override
    public void checkUserExist(UUID userId) {
        if (!userRepository.existsById(userId)) throw new ResourceNotFoundException(String.format("User '%s' not found", userId));
    }

    @Override
    public List<UserDtoOut> findAllUser() {
        return userRepository.findAll().stream().map(EntityDtoMapper.INSTANCE::toUserDtoOut).collect(Collectors.toList());
    }

}
