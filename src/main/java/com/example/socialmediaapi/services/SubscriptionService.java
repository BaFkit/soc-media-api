package com.example.socialmediaapi.services;

import com.example.socialmediaapi.entities.Subscription;
import com.example.socialmediaapi.entities.User;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void subscribe(UUID targetId, String followerUsername) {
        Subscription subscription = new Subscription();
        subscription.setFollower(userService.findUserByUsername(followerUsername));
        subscription.setSubscriptionTarget(userService.findUserById(targetId));
        subscriptionRepository.save(subscription);
    }

    @Transactional
    public void acceptFriendship(UUID targetId, String acceptingUsername) {
        User acceptingUser = userService.findUserByUsername(acceptingUsername);
        if (!checkSubscription(targetId, acceptingUser.getId())) throw new AccessDeniedException("No friend request");
        subscribe(targetId, acceptingUsername);
        creatingFriendship(targetId, acceptingUser.getId());
    }

    private boolean checkSubscription(UUID followerId, UUID subscriptionTargetId) {
       return subscriptionRepository.existsByFollower_IdAndSubscriptionTarget_Id(followerId, subscriptionTargetId);
    }

    private void creatingFriendship(UUID firstUserId, UUID secondUserId) {
        try {
            subscriptionRepository.findByFollower_Id(firstUserId).setFriends(true);
            subscriptionRepository.findByFollower_Id(secondUserId).setFriends(true);
        } catch (Exception e) {
            log.error("Error creating friendship");
        }

    }
}
