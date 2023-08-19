package com.example.socialmediaapi.core.repositories;

import com.example.socialmediaapi.core.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByAddresseeId(UUID addresseeId);

    List<Message> findAllByAddresseeIdAndSenderId(UUID addresseeId, UUID senderId);

    List<Message> findAllBySenderId(UUID addresseeId);
}
