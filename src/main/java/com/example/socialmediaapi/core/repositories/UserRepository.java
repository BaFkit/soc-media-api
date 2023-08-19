package com.example.socialmediaapi.core.repositories;

import com.example.socialmediaapi.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findByEmail(String email);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

}
