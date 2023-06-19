package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class UserContainer {

    private LinkedHashMap<Integer, User> fbUsers;


    public UserContainer() {
        fbUsers = new LinkedHashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("dspadder@hotmail.com");
        user.setPassword("DS2023!!");
        fbUsers.put(user.getId(), user);
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("andrewSmitg@outlook.com");
        user2.setPassword("AS2023!!");
        fbUsers.put(user2.getId(), user2);
        User user3 = new User();
        user3.setId(3);
        user3.setEmail("zkoneisegg@hotmail.com");
        user3.setPassword("ZK2023!!");
        fbUsers.put(user3.getId(), user3);
        User user4 = new User();
        user4.setId(4);
        user4.setEmail("stopmotionformeandyt@gmail.com");
        user4.setPassword("Mate'sApple190");
        fbUsers.put(user4.getId(), user4);
        User user5 = new User();
        user5.setId(5);
        user5.setEmail("mztbusinessenquiries@gmail.com");
        user5.setPassword("Mate'sPC190");
        fbUsers.put(user5.getId(), user5);

    }

    public void addUser(JSONObject userData){
        Integer userId = userData.getInt("userId");
        User user = fbUsers.get(userId);
        if (user != null){
            return;
        }
        User newUser = new User();
        newUser.setEmail(userData.getString("email"));
        newUser.setPassword(userData.getString("password"));
        newUser.setId(userId);
        fbUsers.put(newUser.getId(), newUser);
    }


    public User getFbUserByUserId(Integer userId){
        return fbUsers.get(userId);
    }


    public List<User> getUnderReviewUsers() {
        List<User> UnderReviewUsers = new ArrayList<>();
        fbUsers.forEach((userId, user) -> {
            if (user.isUnderReview()) {

                UnderReviewUsers.add(user);
            }
        });

        return UnderReviewUsers;
    }

    public boolean canPost(User user){

        UserStatus currentStatus = user.getStatus();
        if (currentStatus == UserStatus.INVALID) {
            return false;
        }
        if (currentStatus == UserStatus.IN_USE) {
            return false;
        }

        return true;
    }




}
