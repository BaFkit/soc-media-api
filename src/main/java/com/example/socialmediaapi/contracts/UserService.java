package com.example.socialmediaapi.contracts;

import com.example.socialmediaapi.dto.requests.SignupRequest;
import com.example.socialmediaapi.dto.responces.UserDtoOut;
import com.example.socialmediaapi.core.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    List<UserDtoOut> findAllUser();
    User findUserById(UUID id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    void checkUserExist(UUID userId);
    void createUser(SignupRequest userIn);

}
