package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.models.FinishedCar;
import org.example.springbootapplicationrun.models.Post;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigInteger;

public class GetCarPostedAtPage {

    public void getTimePostedAt(WebDriver driver, FinishedCar car) throws InterruptedException {

        BigInteger carId = car.getCarId();
        System.out.println(carId);
        System.out.println("https://www.facebook.com/marketplace/item/" + carId + "/");

        driver.get("https://www.facebook.com/marketplace/item/" + carId + "/");
        Thread.sleep(2000);

        String postedAt = null;
        try {
            postedAt = driver.findElement(By.xpath("//span[@id =\"ssrb_feed_start\"]/parent::div/child::div/child::div/child::div/child::div/child::div/child::div/child::div/following-sibling::div/child::div/child::div/child::div/child::div/child::div/following-sibling::div/following-sibling::div/child::div/child::div/child::div/child::span")).getText();
            System.out.println(postedAt);

        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
        }


//        driver.findElement(By.xpath("//a [@href=\"/marketplace/budapest/\"]/parent::span/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div/child::div/following-sibling::div/following-sibling::div/following-sibling::div/following-sibling::div/child::div/following-sibling::div/child::div/child::div/child::div/child::span/child::div/child::span")).click();
//        String description = driver.findElement(By.xpath("//a [@href=\"/marketplace/budapest/\"]/parent::span/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div/child::div/following-sibling::div/following-sibling::div/following-sibling::div/following-sibling::div/child::div/following-sibling::div/child::div/child::div/child::div/child::span")).getText();

        car.setPostedAt(postedAt);
//        car.setDescription(description);
        Thread.sleep(2000);



    }


}

