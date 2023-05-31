package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.components.clients.EncryptedClient;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class UserContainer {

    @Autowired
    private EncryptedClient encryptedClient;
    private LinkedHashMap<Integer, User> fbUsers;


    public UserContainer() {
        fbUsers = new LinkedHashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("timapples1974@gmail.com");
        user.setPassword("Mate'sPC190");
        fbUsers.put(user.getId(), user);
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("mattcookzrt@gmail.com");
        user2.setPassword("Mate'sCook190");
        fbUsers.put(user2.getId(), user2);
        User user3 = new User();
        user3.setId(3);
        user3.setEmail("stopmotionformeandyt@gmail.com");
        user3.setPassword("Mate'sApple190");
        fbUsers.put(user3.getId(), user3);

    }


    public User getFbUserByUserId(Integer userId) throws IOException {
        User user = fbUsers.get(userId);
        if(user != null){
            return user;
        }
        User newUser = buildUser(userId);
        fbUsers.put(newUser.getId(), newUser);
        return newUser;
    }

    protected User buildUser(Integer userId) throws IOException {
        User user = encryptedClient.getFbUser(userId);
        return user;
    }

    public List<User> getUnderReviewUsers(){
        List<User> UnderReviewUsers = new ArrayList<>();
        fbUsers.forEach((userId, user) ->{
            if (user.isUnderReview()){

                UnderReviewUsers.add(user);
            }
        });

        return UnderReviewUsers;
    }


}
