package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.components.containers.CarIdsContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.GetCarPostedAtPage;
import org.example.springbootapplicationrun.enums.CarLinksStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.CarId;
import org.example.springbootapplicationrun.models.User;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class GetCarPostedAt {
    @Autowired
    private CarIdsContainer carIdsContainer;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private CarServer carServer;

    @Scheduled(fixedRate = 300_000, initialDelay = 20_000)
    public void getPostedAt() {
        List<CarId> carIds = carIdsContainer.getCarIds();
        carIds.forEach(carId -> {
            try {

                if (carId.getStatus() == CarLinksStatus.FINISHED){
                    return;
                }

                Integer userId = carId.getUserId();
                BigInteger newCarId = carId.getCarId();
                System.out.println(newCarId);
                User user = userContainer.getFbUserByUserId(userId);

                WebDriver driver = facebookBrowser.getBrowser(user);

                GetCarPostedAtPage getCarPostedAtPage = new GetCarPostedAtPage();
                Car newCar = new Car();
                newCar.setMarketplaceId(newCarId);
                getCarPostedAtPage.getTimePostedAt(driver, newCar);
                LocalDateTime time = LocalDateTime.now();
                String postedAt = newCar.getPostedAt();
                String truePostedAt = (postedAt + ":" + String.valueOf(time));
                newCar.setPostedAt(truePostedAt);
                carServer.updateCar(newCar);

                carId.setStatus(CarLinksStatus.FINISHED);

            } catch (Exception e) {
                throw new RuntimeException(e);
                
            }
        });
    }

}
