package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserReviewer {
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private FacebookBrowser facebookBrowser;
    @Scheduled(fixedRate = 60_000)
    public void reviewUsers(){
        List<User> underReviewUsers = userContainer.getUnderReviewUsers();
        underReviewUsers.forEach(user -> {
            LocalDateTime changedAt = user.getStatusChangedAt();
            WebDriver driver = null;
            try {
                driver = facebookBrowser.getBrowser(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(changedAt.plusMinutes(30).isAfter(LocalDateTime.now())){
                try {
                    driver.findElement(By.xpath("//span [@class][contains(text(), 'What's on your mind')]/parent::div/parent::div")).click();
                }catch (Exception e){
                    String message = e.getMessage();
                    System.out.println(message);
                    user.setStatus(UserStatus.INVALID);
                    facebookBrowser.closeBrowser(user);
                    throw e;
                }
                user.setStatus(UserStatus.VALID);
            }
        });


        }

    }

