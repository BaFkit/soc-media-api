package com.example.socialmediaapi.core.repositories;

import com.example.socialmediaapi.core.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByFollowerIdAndSubscriptionTargetId(UUID followerId, UUID subscriptionTargetId);
    boolean existsByFollowerIdAndSubscriptionTargetId(UUID followerId, UUID subscriptionTargetId);

}
