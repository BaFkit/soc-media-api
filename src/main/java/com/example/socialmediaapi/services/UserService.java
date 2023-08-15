package com.example.socialmediaapi.services;

import com.example.socialmediaapi.entities.User;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = login.contains("@") ? findUserByEmail(login) : findUserByUsername(login);
        return build(user);
    }

    public static User build(User user) {
        Collection<?extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    private User findUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new ResourceNotFoundException(String.format("User with email: '%s' not found", email));
        return user;
    }

    private User findUserByEmail(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ResourceNotFoundException(String.format("User '%s' not found", username));
        return user;
    }
}
