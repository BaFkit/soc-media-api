package com.example.socialmediaapi.services;

import com.example.socialmediaapi.dto.SignupRequest;
import com.example.socialmediaapi.entities.Role;
import com.example.socialmediaapi.entities.User;
import com.example.socialmediaapi.exceptions.ResourceExistsException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = login.contains("@") ? findUserByEmail(login) : findUserByUsername(login);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    private User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new ResourceNotFoundException(String.format("User with email: '%s' not found", email));
        return user;
    }

    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ResourceNotFoundException(String.format("User '%s' not found", username));
        return user;
    }

//    private User findUserById(UUID id) {
//        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//    }

    @Transactional
    public void createUser(SignupRequest userIn) {
        User user = new User();
        if (Boolean.TRUE.equals(userRepository.existsUserByUsername(userIn.getUsername()))) throw new ResourceExistsException("User with this username already exists");
        if (Boolean.TRUE.equals(userRepository.existsUserByEmail(userIn.getEmail()))) throw new ResourceExistsException("User with this email already exists");
        user.setUsername(userIn.getUsername());
        user.setEmail(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleService.findRoleByName("ROLE_USER"));
        user.setRoles(userRoles);
        log.info("Saving User {}", userIn.getUsername());
        userRepository.save(user);
    }

}
