package com.example.socialmediaapi.repositories;

import com.example.socialmediaapi.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByFollower_IdAndSubscriptionTarget_Id(UUID followerId, UUID subscriptionTargetId);
    boolean existsByFollower_IdAndSubscriptionTarget_Id(UUID followerId, UUID subscriptionTargetId);

}
