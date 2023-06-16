package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.components.clients.CarServer;
import org.example.springbootapplicationrun.models.Car;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarketplacePage {

    @Autowired
    private CarServer carServer;

    WebDriver driver;

    public MarketplacePage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Car> getCars(String facebookLink) throws InterruptedException {

        driver.get(facebookLink);
        Thread.sleep(2000);

        List<Car> carsInfo = new ArrayList<>();

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

                BigInteger marketplaceId = regexMarketplaceId(car);
                car.setMarketplaceId(marketplaceId);

                carsInfo.add(car);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        });
        return carsInfo;
    }

    private void scrollDown() throws InterruptedException {

        for (int i = 1; i <= 10; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(4000);
        }
    }

    private BigInteger regexMarketplaceId(Car car) throws Exception {
        Pattern pattern = Pattern.compile("/item/([0-9]+)/");
        Matcher matcher = pattern.matcher(car.getLink());

        if (!matcher.find()) {
            throw new Exception("No Marketplace ID was found!!");
        }
        String marketplaceId = matcher.group(1);

        return new BigInteger(marketplaceId);
    }

}
