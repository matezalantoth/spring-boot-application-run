package org.example.springbootapplicationrun.components;

import org.example.springbootapplicationrun.models.User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
@Component
public class UserContainer {
    private LinkedHashMap<Integer, User> users;


    public UserContainer() {
        users = new LinkedHashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("timapples1974@gmail.com");
        user.setPassword("Mate'sPC190");
        users.put(user.getId(), user);
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("mattcookzrt@gmail.com");
        user2.setPassword("Mate'sCook190");
        users.put(user2.getId(), user2);
        User user3 = new User();
        user3.setId(3);
        user3.setEmail("stopmotionformeandyt@gmail.com");
        user3.setPassword("Mate'sApple190");
        users.put(user3.getId(), user3);

    }


    public User getUserByUserId(Integer userId) {
        return users.get(userId);
    }

}
