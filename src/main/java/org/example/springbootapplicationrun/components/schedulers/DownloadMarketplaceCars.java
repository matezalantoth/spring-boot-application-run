package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.components.containers.CarUserContainer;
import org.example.springbootapplicationrun.components.containers.ImageContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.example.springbootapplicationrun.components.updaters.UserUpdater;
import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.ImageStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Image;
import org.example.springbootapplicationrun.models.User;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
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
    @Autowired
    private ImageContainer imageContainer;


    @Scheduled(fixedRate = 600_000, initialDelay = 15_000)
    public void downloadCars() throws Exception {
        Car car = new Car();

        List<User> users = carUserContainer.getQueue();

            users.forEach(user -> {
                try {

                    String link = getLinks(user);
                    car.setCarsStatus(GetCarsStatus.IN_PROGRESS);

                    WebDriver driver = facebookBrowser.getBrowser(user);

                    LocalDateTime changedAt = user.getStatusChangedAt();

                    if (changedAt.plusHours(6).isBefore(LocalDateTime.now())) {
                        userUpdater.updateStatus(user, UserStatus.ON_COOLDOWN);
                        return;
                    }

                    UserStatus currentStatus = user.getStatus();
                    if (currentStatus == UserStatus.INVALID || currentStatus == UserStatus.ON_COOLDOWN) {
                        return;
                    }

                    MarketplacePage marketplacePage = new MarketplacePage(driver, imageContainer);
                    List<Car> carsInfo = marketplacePage.getCars(link);
                    carServer.sendCarsToServer(carsInfo);
                    carsInfo.forEach(finalCar -> {

                        Image image = imageContainer.getImageByMarketplaceId(finalCar.getMarketplaceId());

                        ImageStatus imageStatus = image.getStatus();
                        if (imageStatus == ImageStatus.DOWNLOADED){
                            return;
                        }

                        try {

                            finalCar.setImageContent(image.getImageContent());
                            carServer.updateCar(finalCar);
                            image.setStatus(ImageStatus.DOWNLOADED);
                            Thread.sleep(1000);

                        } catch (Exception e) {

                            String message = e.getMessage();
                            System.out.println(message);
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
    }

    public String getLinks(User user){
        if (user.getId() == 1) {
            return "https://www.facebook.com/marketplace/budapest/vehicles?minPrice=0&maxPrice=1000000&sortBy=creation_time_descend&exact=false";
        }
        Integer userId = user.getId();
        return "https://www.facebook.com/marketplace/budapest/vehicles?minPrice=" + (userId-1) +"000000&maxPrice=" + userId+ "000000&sortBy=creation_time_descend&exact=false";
    }
}

//                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
//                car.setCarsStatus(GetCarsStatus.FAILED);

