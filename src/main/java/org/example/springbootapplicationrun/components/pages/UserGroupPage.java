package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.GroupInfo;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class UserGroupPage {

    WebDriver driver;


    public UserGroupPage(WebDriver driver) {this.driver = driver;}

    public JSONArray getGroups() throws InterruptedException {

        driver.get("https://www.facebook.com/groups/joins");

        Thread.sleep(2000);

        JSONArray groups = new JSONArray();

        scrollDown();

            driver.findElements(By.xpath("//a [@aria-label='View group']")).forEach(groupLink -> {

            GroupInfo group = new GroupInfo();

            group.setLink(groupLink.getAttribute("href"));
            System.out.println(groupLink.getAttribute("href"));

            group.setName(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/child::div/following-sibling::div/div/div/span/span/div/a")).getText());
            System.out.println(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/child::div/following-sibling::div/div/div/span/span/div/a")).getText());

            group.setImage(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/div/a/div/div//*//*/following-sibling::*//*")).getAttribute("xlink:href"));
            System.out.println(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/div/a/div/div//*//*/following-sibling::*//*")).getAttribute("xlink:href"));

            JSONObject jsonObject = group.getJSONGroupInfo();
            groups.put(jsonObject);


        });
    return groups;

    }

    private void scrollDown() throws InterruptedException {

        for (int i = 1; i <= 50; i++) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(2000);
            System.out.println(i);

        }


    }




}
