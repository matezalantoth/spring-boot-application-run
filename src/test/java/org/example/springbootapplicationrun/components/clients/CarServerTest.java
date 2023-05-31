package org.example.springbootapplicationrun.components.clients;

import org.example.springbootapplicationrun.models.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarServerTest {

    @Autowired
    private CarServer carServer;

    @Test
    void contextLoads() {
        JSONArray carsJson = new JSONArray();
        Car car = new Car();

        car.setTitle("Image Title");
        car.setImage("Image Image");
        car.setLink("Image Link");
        car.setPrice("£££");
        car.setDistance("Image Distance");

        JSONObject jsonObject = car.getJSONInfo();
        carsJson.put(jsonObject);


        try {
            carServer.sendCarsToServer(carsJson);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
