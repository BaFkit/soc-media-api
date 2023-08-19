package com.example.socialmediaapi.services;

import com.example.socialmediaapi.entities.Subscription;
import com.example.socialmediaapi.entities.User;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.exceptions.UnacceptableException;
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
            findSubscription(firstUserId, secondUserId).setFriends(true);
            findSubscription(firstUserId, secondUserId).setFriends(true);
        } catch (Exception e) {
            log.error("Error creating friendship: " + e.getMessage());
        }
    }

    private Subscription findSubscription(UUID followerId, UUID subscriptionTargetId) {
        return subscriptionRepository.findByFollower_IdAndSubscriptionTarget_Id(followerId, subscriptionTargetId).orElseThrow(() -> new ResourceNotFoundException("No subscription"));
    }

    @Transactional
    public void unsubscribe(UUID targetId, String initiatorUsername) {
        Subscription subscription = findSubscription(userService.findUserByUsername(initiatorUsername).getId(), targetId);
        if (subscription.isFriends()) throw new UnacceptableException("Can't unfollow a friend");
        subscriptionRepository.delete(subscription);
    }

    @Transactional
    public void unfriend(UUID targetId, String initiatorUsername) {
        User initiatorUser = userService.findUserByUsername(initiatorUsername);
        Subscription subscription = findSubscription(initiatorUser.getId(), targetId);
        if (!subscription.isFriends()) throw new ResourceNotFoundException("This user is not a friend");
        subscriptionRepository.delete(subscription);
        findSubscription(targetId, initiatorUser.getId()).setFriends(false);
    }

}
