package com.example.socialmediaapi.contracts;

import java.util.UUID;

public interface SubscriptionService {

    void subscribe(UUID targetId, String followerUsername);
    void acceptFriendship(UUID targetId, String acceptingUsername);
    void unsubscribe(UUID targetId, String initiatorUsername);
    void unfriend(UUID targetId, String initiatorUsername);
    boolean checkFriendship(UUID firstUserId, UUID secondUserId);

}
