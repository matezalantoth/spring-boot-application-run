package org.example.springbootapplicationrun.components;

import com.opencsv.CSVWriter;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DownloadMarketplaceCars {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;


    @Scheduled(fixedRate = 3_600_000)
    public void downloadCars() throws InterruptedException {
        User user = userContainer.getUserByUserId(3);

        WebDriver driver = facebookBrowser.getBrowser(user.getEmail(), user.getPassword());

        MarketplacePage marketplacePage = new MarketplacePage(driver);
        List<Car> cars = marketplacePage.getCars();

        List<String[]> strList = cars.stream()
                .map(Car::toArray)
                .collect(Collectors.toList());

        String time = String.valueOf(Instant.now().getEpochSecond());
        System.out.println(time);

        try (CSVWriter writer = new CSVWriter(new FileWriter("/home/matezalantoth/Downloads/Cars_" + time + ".csv"))) {
            writer.writeAll(strList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.get("https://www.facebook.com");
        Thread.sleep(2000);




    }
}
