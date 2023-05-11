package org.example.springbootapplicationrun.components;

import models.User;

import java.util.LinkedHashMap;

public class UserContainer {


    private static UserContainer INSTANCE;
    private LinkedHashMap<Integer, User> users;

    public static UserContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserContainer();

        }

        return INSTANCE;
    }

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

    }


    public User getUserByUserId(Integer userId) {
        return users.get(userId);
    }

}
