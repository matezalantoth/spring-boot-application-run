package org.example.springbootapplicationrun.components.pages;

import org.example.springbootapplicationrun.models.Car;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class MarketplacePage {

    WebDriver driver;

    public MarketplacePage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Car> getCars() throws InterruptedException {

        driver.get("https://www.facebook.com/marketplace/category/cars?sortBy=creation_time_descend&exact=false");
        Thread.sleep(2000);

        scrollDown();

        List<Car> cars = new ArrayList<Car>();

        driver.findElements(By.xpath("//img")).forEach(image -> {

            String name = image.getAttribute("alt");
            if (name.equals("Loading more items") || (name.isEmpty())) {
                return;
            }

            System.out.println(image.getAttribute("alt"));
            System.out.println(image.getAttribute("src"));

            WebElement link = image.findElement(By.xpath("./ancestor::a"));
            System.out.println(link.getAttribute("href"));
            try {

                WebElement distance = link.findElement(By.xpath("./div/div/following-sibling::div/div/div/span/span"));
                System.out.println(distance.getText());

                WebElement price = link.findElement(By.xpath("./div/div/following-sibling::div/div/span/div/span"));
                System.out.println(price.getText());

                Car car = new Car();

                car.setTitle(image.getAttribute("alt"));
                car.setImage(image.getAttribute("src"));
                car.setLink(link.getAttribute("href"));
                car.setPrice(price.getText());
                car.setDistance(distance.getText());
                cars.add(car);

            } catch (NoSuchElementException e) {
                return;
            }

        });

        System.out.println(cars.size());
        return cars;

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
