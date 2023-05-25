package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class DownloadMarketplaceCars {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private CarServer carServer;


    @Scheduled(fixedRate = 3_600_000)
    public void downloadCars() throws InterruptedException, IOException {

        User user = userContainer.getFbUserByUserId(3);

        WebDriver driver = facebookBrowser.getBrowser(user);

        UserStatus currentStatus = user.getStatus();
        if (currentStatus == UserStatus.INVALID){
            return;
        }
        try {

            MarketplacePage marketplacePage = new MarketplacePage(driver);
            JSONArray cars = marketplacePage.getCars();
            carServer.sendCarsToServer(cars);

        }catch(Exception e){
            user.setStatus(UserStatus.INVALID);
        }

        String time = String.valueOf(Instant.now().getEpochSecond());
        System.out.println(time);

        driver.get("https://www.facebook.com");
        Thread.sleep(2000);

        System.out.println(currentStatus);

        UserReport userReport = new UserReport();
        UserClient userStatusServer = new UserClient();

        System.out.println(currentStatus);
        userReport.setUserStatus(user.getStatus());
        userReport.setId(user.getId());

        JSONObject userStatus = userReport.getUserStatusJSON();
        userStatusServer.sendUserInfoToServer(userStatus);


    }
}
