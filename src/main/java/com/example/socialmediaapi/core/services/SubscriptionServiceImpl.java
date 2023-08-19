package com.example.socialmediaapi.core.services;

import com.example.socialmediaapi.contracts.SubscriptionService;
import com.example.socialmediaapi.contracts.UserService;
import com.example.socialmediaapi.core.entities.Subscription;
import com.example.socialmediaapi.core.entities.User;
import com.example.socialmediaapi.exceptions.AccessDeniedException;
import com.example.socialmediaapi.exceptions.ResourceExistsException;
import com.example.socialmediaapi.exceptions.ResourceNotFoundException;
import com.example.socialmediaapi.exceptions.UnacceptableException;
import com.example.socialmediaapi.core.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public void subscribe(UUID targetId, String followerUsername) {
        User follower = userService.findUserByUsername(followerUsername);
        if (checkSubscription(follower.getId(), targetId)) throw new ResourceExistsException("Already have a subscription");
        Subscription subscription = new Subscription();
        subscription.setFollower(follower);
        subscription.setSubscriptionTarget(userService.findUserById(targetId));
        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public void acceptFriendship(UUID targetId, String acceptingUsername) {
        User acceptingUser = userService.findUserByUsername(acceptingUsername);
        if (!checkSubscription(targetId, acceptingUser.getId())) throw new AccessDeniedException("No friend request");
        if (!checkSubscription(acceptingUser.getId(), targetId)) subscribe(targetId, acceptingUsername);
        creatingFriendship(targetId, acceptingUser.getId());
    }

    private boolean checkSubscription(UUID followerId, UUID subscriptionTargetId) {
       return subscriptionRepository.existsByFollowerIdAndSubscriptionTargetId(followerId, subscriptionTargetId);
    }

    @Override
    public boolean checkFriendship(UUID firstUserId, UUID secondUserId) {
        return findSubscription(firstUserId, secondUserId).isFriends();
    }

    private void creatingFriendship(UUID firstUserId, UUID secondUserId) {
        if (checkFriendship(firstUserId, secondUserId)) throw new ResourceExistsException("Already have a friendship");
        try {
            findSubscription(firstUserId, secondUserId).setFriends(true);
            findSubscription(secondUserId, firstUserId).setFriends(true);
        } catch (Exception e) {
            log.error("Error creating friendship: " + e.getMessage());
        }
    }

    private Subscription findSubscription(UUID followerId, UUID subscriptionTargetId) {
        return subscriptionRepository.findByFollowerIdAndSubscriptionTargetId(followerId, subscriptionTargetId).orElseThrow(() -> new ResourceNotFoundException("No subscription"));
    }

    @Override
    @Transactional
    public void unsubscribe(UUID targetId, String initiatorUsername) {
        Subscription subscription = findSubscription(userService.findUserByUsername(initiatorUsername).getId(), targetId);
        if (subscription.isFriends()) throw new UnacceptableException("Can't unfollow a friend");
        subscriptionRepository.delete(subscription);
    }

    @Override
    @Transactional
    public void unfriend(UUID targetId, String initiatorUsername) {
        User initiatorUser = userService.findUserByUsername(initiatorUsername);
        Subscription subscription = findSubscription(initiatorUser.getId(), targetId);
        if (!subscription.isFriends()) throw new ResourceNotFoundException("This user is not a friend");
        subscriptionRepository.delete(subscription);
        findSubscription(targetId, initiatorUser.getId()).setFriends(false);
    }

}
