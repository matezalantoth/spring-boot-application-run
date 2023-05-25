package org.example.springbootapplicationrun.components.browsers;

import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.components.pages.LoginPage;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
@Component
public class FacebookBrowser {
    private LinkedHashMap<String, WebDriver> drivers;
    public FacebookBrowser() {
        drivers = new LinkedHashMap<>();

    }
    public WebDriver getBrowser(User user) throws InterruptedException {
        String email = user.getEmail();
        if (drivers.containsKey(email)){
            return  drivers.get(email);
        }


        return createAndLogin(user);

    }

    protected WebDriver createAndLogin(User user) throws InterruptedException {

        String email = user.getEmail();
        String password = user.getPassword();

        String chromeDriverPath = "drivers/chromedriver";
        String path = "";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--log-path=" + path + chromeDriverPath);
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        System.setProperty("webdriver.chrome.driver", path + chromeDriverPath);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        try {

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(email, password);
        }catch(Exception e){
            user.setStatus(UserStatus.INVALID);
            throw e;
        }
        UserStatus currentStatus = user.getStatus();

        System.out.println(currentStatus);

        UserReport userReport = new UserReport();
        UserClient userStatusServer = new UserClient();

        System.out.println(currentStatus);
        userReport.setUserStatus(user.getStatus());
        userReport.setId(user.getId());

        JSONObject userStatus = userReport.getUserStatusJSON();
        userStatusServer.sendUserInfoToServer(userStatus);

        drivers.put(email,driver);

        return driver;

    }


}
