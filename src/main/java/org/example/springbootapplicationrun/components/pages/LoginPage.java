package org.example.springbootapplicationrun.components.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) throws InterruptedException {

        driver.get("https://www.facebook.com/login/");

        driver.findElement(By.xpath("//button[@data-cookiebanner='accept_button']")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        Thread.sleep(10000);

        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        Thread.sleep(5000);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);

    }

}
