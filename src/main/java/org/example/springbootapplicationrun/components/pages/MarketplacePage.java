package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.models.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;

public class MarketplacePage {

    @Autowired
    private CarServer carServer;

    WebDriver driver;

    public MarketplacePage(WebDriver driver) {
        this.driver = driver;
    }

    public JSONArray getCars() throws InterruptedException {

        driver.get("https://www.facebook.com/marketplace/category/vehicles?sortBy=creation_time_descend&exact=true");
        Thread.sleep(2000);

        JSONArray carsInfo = new JSONArray();

        scrollDown();

        driver.findElements(By.xpath("//img")).forEach(image -> {

            try {

                String name = image.getAttribute("alt");
                if (name.equals("Loading more items") || (name.isEmpty())) {
                    return;
                }

                System.out.println(image.getAttribute("alt"));
                System.out.println(image.getAttribute("src"));

                WebElement link = image.findElement(By.xpath("./ancestor::a"));
                System.out.println(link.getAttribute("href"));


                String distanceText = null;
                try {
                    WebElement distance = link.findElement(By.xpath("./div/div/following-sibling::div/div/div/span/span"));
                    distanceText = distance.getText();
                    System.out.println(distanceText);
                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);

                }

                WebElement price = link.findElement(By.xpath("./div/div/following-sibling::div/div/span/div/span"));
                System.out.println(price.getText());

                Car car = new Car();

                car.setTitle(image.getAttribute("alt"));
                car.setImage(image.getAttribute("src"));
                car.setLink(link.getAttribute("href"));
                car.setPrice(price.getText());
                car.setDistance(distanceText);

                JSONObject jsonObject = car.getJSONInfo();
                carsInfo.put(jsonObject);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        });
        return carsInfo;
    }

    private void scrollDown() throws InterruptedException {

        for (int i = 1; i <= 50; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(3000);
        }
    }

}
