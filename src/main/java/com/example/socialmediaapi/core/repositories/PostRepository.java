package com.example.socialmediaapi.core.repositories;

import com.example.socialmediaapi.core.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByUserId(UUID userId);

    @Query(value = "select p from Post p, Subscription s where p.user.id = s.subscriptionTarget and s.follower =:userId")
    Page<Post> findSubscriberPosts(Pageable page, UUID userId);

}
