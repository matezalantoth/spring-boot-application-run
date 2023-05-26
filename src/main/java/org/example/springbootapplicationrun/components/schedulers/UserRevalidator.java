package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@Component
public class UserRevalidator {
    @Autowired
    private UserContainer userContainer;
    @Scheduled(fixedRate = 60000)
    public void reactivateInvalidUsers(){
        List<User> invalidUsers = userContainer.getInvalidUsers();
        invalidUsers.forEach(user -> {
            LocalDateTime changedAt = user.getStatusChangedAt();
            if(changedAt.plusMinutes(30).isAfter(LocalDateTime.now())){
                user.setStatus(UserStatus.VALID);
            }
        });


        }

    }

