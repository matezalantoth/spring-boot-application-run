package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.updaters.UserUpdater;
import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.containers.CarUserContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class DownloadMarketplaceCars {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private CarServer carServer;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private CarUserContainer carUserContainer;

    @Scheduled(fixedRate = 320_000, initialDelay = 15_000)
    public void downloadCars() throws Exception {
        Car car = new Car();

        List<User> users = carUserContainer.getQueue();
        users.forEach(user -> {
            try {

                car.setCarsStatus(GetCarsStatus.IN_PROGRESS);

                Integer userId = user.getId();

                User trueUser = userContainer.getFbUserByUserId(userId);

                WebDriver driver = facebookBrowser.getBrowser(trueUser);

                UserStatus currentStatus = user.getStatus();
                if (currentStatus == UserStatus.INVALID) {
                    return;
                }

                MarketplacePage marketplacePage = new MarketplacePage(driver);
                JSONArray carsInfo = marketplacePage.getCars();
                carServer.sendCarsToServer(carsInfo);


                String time = String.valueOf(Instant.now().getEpochSecond());
                System.out.println(time);

                driver.get("https://www.facebook.com");
                Thread.sleep(2000);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
                car.setCarsStatus(GetCarsStatus.FAILED);
            }
        });
    }
}


