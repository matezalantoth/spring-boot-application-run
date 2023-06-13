package org.example.springbootapplicationrun.components.updaters;

import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class UserUpdater {

    @Autowired
    private UserClient userClient;

    public void updateStatus(User user, UserStatus status) {

        LocalDateTime time = LocalDateTime.now();

        user.setStatus(status);
        user.setStatusChangedAt(time);

        UserReport userReport = new UserReport();

        userReport.setId(user.getId());
        userReport.setUserStatus(user.getStatus());
        userReport.setStatusChangedAt(user.getStatusChangedAt());

        JSONObject userStatus = userReport.getUserStatusJSON();
        userClient.sendUserInfoToServer(userStatus);


    }
}
