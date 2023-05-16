package org.example.springbootapplicationrun.components;

import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.MarketplacePage;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DownloadMarketplaceCars {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private CarServer carServer;


    @Scheduled(fixedRate = 3_600_000)
    public void downloadCars() throws InterruptedException {

        User user = userContainer.getUserByUserId(3);

        WebDriver driver = facebookBrowser.getBrowser(user.getEmail(), user.getPassword());

        MarketplacePage marketplacePage = new MarketplacePage(driver);
        JSONArray cars = marketplacePage.getCars();
        carServer.sendCarsToServer(cars);

        String time = String.valueOf(Instant.now().getEpochSecond());
        System.out.println(time);

        driver.get("https://www.facebook.com");
        Thread.sleep(2000);


    }
}
