package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.updaters.UserUpdater;
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
    @Autowired
    private UserUpdater userUpdater;

    @Scheduled(fixedRate = 60_000)
    public void reviewUsers() {

        List<User> underReviewUsers = userContainer.getUnderReviewUsers();
        underReviewUsers.forEach(user -> {
            LocalDateTime changedAt = user.getStatusChangedAt();

            if (changedAt.plusHours(18).isBefore(LocalDateTime.now())) {
                return;
            }

            if (user.getStatus() == UserStatus.ON_COOLDOWN){
                userUpdater.updateStatus(user, UserStatus.VALID);
            }

            WebDriver driver;

            try {
                driver = facebookBrowser.getBrowser(user);
            } catch (Exception e) {
                userInvalidator(e, user);
                return;
            }

            try {
                driver.get("https://www.facebook.com");
                driver.findElement(By.xpath("//span [@class][contains(text(), 'What's on your mind')] /parent::div/parent::div")).click();
            } catch (Exception e) {
                userInvalidator(e, user);
                return;
            }

            userUpdater.updateStatus(user, UserStatus.VALID);

        });

    }

    protected void userInvalidator(Exception e, User user) {

        String message = e.getMessage();
        System.out.println(message);
        userUpdater.updateStatus(user, UserStatus.INVALID);
        facebookBrowser.closeBrowser(user);

    }

}

