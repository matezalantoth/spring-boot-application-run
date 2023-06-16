package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.components.containers.CarUserContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.example.springbootapplicationrun.components.updaters.UserUpdater;
import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Image;
import org.example.springbootapplicationrun.models.User;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
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


    @Scheduled(fixedRate = 600_000, initialDelay = 15_000)
    public void downloadCars() throws Exception {
        Car car = new Car();

        ArrayList<String> links = new ArrayList<>();
        links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=0&maxPrice=1000000&sortBy=creation_time_descend&exact=false");
        links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=1000000&maxPrice=2000000&sortBy=creation_time_descend&exact=false");
        links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=2000000&maxPrice=3000000&sortBy=creation_time_descend&exact=false");
        links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=3000000&maxPrice=4000000&sortBy=creation_time_descend&exact=false");
        List<User> users = carUserContainer.getQueue();
        links.forEach(link -> {

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
                    List<Car> carsInfo = marketplacePage.getCars(link);
                    carServer.sendCarsToServer(carsInfo);
                    carsInfo.forEach(finalCar -> {

                        String url = finalCar.getImage();
                        Image image = new Image();
                        image.setUrl(url);

                        try {

                            String imageAsString = image.getImageContent();
                            finalCar.setImageContent(imageAsString);

                            carServer.updateCar(finalCar);
                            Thread.sleep(1000);

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    String time = String.valueOf(Instant.now().getEpochSecond());
                    System.out.println(time);

                    driver.get("https://www.facebook.com");
                    Thread.sleep(2000);

                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                }
            });

        });


    }
}

//                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
//                car.setCarsStatus(GetCarsStatus.FAILED);

