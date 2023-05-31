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
            Thread.sleep(2000);

            form.findElement(By.xpath("(//div [@aria-label = 'Write something...'])[2]")).sendKeys(post.getTitle() + "\n" + post.getPrice() + "\n" + post.getDescription() + "\n" + post.getLink());
            Thread.sleep(30_000);

            form.findElement(By.xpath("(//div [@aria-label = 'Post'])[2]")).click();
            Thread.sleep(2000);


    }


}

