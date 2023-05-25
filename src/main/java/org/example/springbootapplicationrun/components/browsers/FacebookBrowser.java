package org.example.springbootapplicationrun.components.browsers;

import org.example.springbootapplicationrun.components.pages.LoginPage;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
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
    public WebDriver getBrowser(String email, String password, UserStatus status, Integer id) throws InterruptedException {
        if (drivers.containsKey(email)){
            return  drivers.get(email);
        }


        return createAndLogin(email, password, status, id);

    }

    protected WebDriver createAndLogin(String email, String password, UserStatus status, Integer id) throws InterruptedException {

        String chromeDriverPath = "drivers/chromedriver";
        String path = "";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--log-path=" + path + chromeDriverPath);
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        System.setProperty("webdriver.chrome.driver", path + chromeDriverPath);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password, status, id);

        drivers.put(email,driver);

        return driver;

    }


}
