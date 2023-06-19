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

        ArrayList<String> links = getLinks();

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

        });


    }

    public ArrayList<String> getLinks(){
        ArrayList<String> links = new ArrayList<>();
        links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice=0&maxPrice=1000000&sortBy=creation_time_descend&exact=false");

        for (int i = 1; i <= 20; i++) {
            links.add("https://www.facebook.com/marketplace/budapest/vehicles?minPrice="+ i +"000000&maxPrice="+ (i+1) +"000000&sortBy=creation_time_descend&exact=false");
        }
        return links;
    }
}

//                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
//                car.setCarsStatus(GetCarsStatus.FAILED);

