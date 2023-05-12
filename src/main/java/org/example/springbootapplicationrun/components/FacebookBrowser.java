package org.example.springbootapplicationrun.components;

import org.example.springbootapplicationrun.components.pages.LoginPage;
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
    public WebDriver getBrowser(String email, String password) throws InterruptedException {
        if (drivers.containsKey(email)){
            return  drivers.get(email);
        }

        return createAndLogin(email, password);

    }

    protected WebDriver createAndLogin(String email, String password) throws InterruptedException {

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
        loginPage.login(email, password);

        drivers.put(email,driver);

        return driver;

    }


}
