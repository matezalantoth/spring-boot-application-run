package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Objects;

public class GroupPage {

    public void sendPost(Post post, WebDriver driver) throws InterruptedException {

        driver.get("https://www.facebook.com/groups/" + post.getFacebookGroupId());
        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[text() = 'Write something...']")).click();
        Thread.sleep(2000);

        WebElement form = driver.findElement(By.xpath("(//form[@method = 'POST'])[2]"));
        WebElement formfield = form.findElement(By.xpath("//div[@aria-label = 'Write something...']"));
        Thread.sleep(2000);

        form.findElement(By.xpath("//input [@type='file']")).sendKeys(post.getImageLocation());
        Thread.sleep(5000);

        form.findElement(By.xpath("(//div [@aria-label = 'Write something...'])[2]")).sendKeys(post.getTitle() + "\n" + post.getPrice() + "\n" + post.getDescription() + "\n" + post.getLink());
        Thread.sleep(5000);

        form.findElement(By.xpath("(//div [@aria-label = 'Post'])[2]")).click();
        Thread.sleep(10_000);


    }

    public void postListing(Post post, WebDriver driver) throws InterruptedException{
        driver.get("https://www.facebook.com/groups/" + post.getFacebookGroupId());
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@aria-label='Sell Something']")).click();
        Thread.sleep(15000);

        driver.findElement(By.xpath("(//i [@data-visualcompletion=\"css-img\"]/parent::div/parent::div/parent::div/parent::div/parent::div[@role=\"button\"])[3]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@accept=\"image/*,image/heif,image/heic\"]")).sendKeys(post.getImageLocation());
        Thread.sleep(5000);

        driver.findElement(By.xpath("//label[@aria-label=\"Property for sale or rent\"]")).click();
        Thread.sleep(5000);

        rentOrForSale(post, driver);
        Thread.sleep(5000);

        driver.findElement(By.xpath("//div[@aria-label = \"Next\"]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//div[@aria-label = \"Post\"]")).click();

        }

    public void rentOrForSale(Post post, WebDriver driver) throws InterruptedException{

        if (post.getRoS() == 0) {

            driver.findElement(By.xpath("//div [@role = 'option']")).click();

            driver.findElement(By.xpath("//label [@aria-label=\"Type of property for rent\"]")).click();

            typeOfHouseSelector(post, driver);
            Thread.sleep(2000);
            textBoxes(post, driver);
            Thread.sleep(2000);
            washingMachineOrDryer(post, driver);
            Thread.sleep(2000);
            parkingType(post, driver);
            Thread.sleep(2000);
            airConditioningType(post, driver);
            Thread.sleep(2000);
            heatingType(post, driver);
            Thread.sleep(2000);
            petFriendly(post, driver);
            Thread.sleep(2000);

            return;
        }

        if (post.getRoS() == 1) {

            driver.findElement(By.xpath("(//div [@role = 'option'])[2]")).click();

            typeOfHouseSelector(post, driver);
            Thread.sleep(2000);
            textBoxes(post, driver);
            Thread.sleep(2000);
            washingMachineOrDryer(post, driver);
            Thread.sleep(2000);
            parkingType(post, driver);
            Thread.sleep(2000);
            airConditioningType(post, driver);
            Thread.sleep(2000);
            heatingType(post, driver);
            Thread.sleep(2000);

        }

    }
//Select rentOrSaleDrop = new Select(driver.findElement(By.xpath("//div [@role = 'option']")));

    public void typeOfHouseSelector(Post post, WebDriver driver){

        if (post.getToH() == 0){
            driver.findElement(By.xpath("//div [@role = \"option\"]")).click();
            return;
        }
        if (post.getToH() == 1){
            driver.findElement(By.xpath("(//div [@role = \"option\"])[2]")).click();
            return;
        }
        if (post.getToH() == 2){
            driver.findElement(By.xpath("(//div [@role = \"option\"])[3]")).click();
        }
    }

    public void textBoxes(Post post, WebDriver driver) throws InterruptedException{

        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[2]")).sendKeys(post.getBed());
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[3]")).sendKeys(post.getBath());
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[4]")).sendKeys(post.getPrice() + "ft");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[5]")).sendKeys(post.getAddress());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div [@role = \"option\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//textarea [@dir=\"ltr\"]")).sendKeys(post.getDescription());
        Thread.sleep(5000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[6]")).sendKeys(post.getPropSize());
        Thread.sleep(2000);
        if (post.getRoS() == 0) {
            driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[7]")).sendKeys(post.getAvailAt());
            Thread.sleep(2000);
        }

    }

    public void washingMachineOrDryer(Post post, WebDriver driver){

        driver.findElement(By.xpath("//label[@aria-label = \"Washing machine/dryer\"]")).click();

        if (post.getWMoD() == 0){
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getWMoD() == 1){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getWMoD() == 2){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getWMoD() == 3){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
        }


    }

    public void parkingType(Post post, WebDriver driver){

        driver.findElement(By.xpath("//label[@aria-label=\"Parking type\"]")).click();

        if (post.getpT() == 0){
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getpT() == 1){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getpT() == 2){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getpT() == 3){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
            return;
        }
        if (post.getpT() == 4){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[5]")).click();

        }
    }

    public void airConditioningType(Post post, WebDriver driver){
        driver.findElement(By.xpath("//label[@aria-label=\"Air conditioning\"]")).click();

        if (post.getACT() == 0){
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getACT() == 1){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getACT() == 2){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
        }


    }

    public void heatingType(Post post, WebDriver driver){
        driver.findElement(By.xpath("//label[@aria-label=\"Heating type\"]")).click();

        if (post.getHT() == 0){
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getHT() == 1){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getHT() == 2){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getHT() == 3){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
            return;
        }
        if (post.getHT() == 4){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[5]")).click();
            return;
        }
        if (post.getHT() == 5){
            driver.findElement(By.xpath("(//div[@role=\"option\"])[6]")).click();
        }

    }

    public void petFriendly(Post post, WebDriver driver){
        if (post.getPF() == 0){
            driver.findElement(By.xpath("(//input[@role = \"switch\"])[2]")).click();
            return;
        }
        if (post.getPF() == 1){
            driver.findElement(By.xpath("(//input[@role = \"switch\"])[3]")).click();
        }
    }


}

