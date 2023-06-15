package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.models.CarId;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarIdsContainer {
    private List<CarId> carIds;

    public CarIdsContainer() {
        this.carIds = new ArrayList<>();
    }

    public void addPusherData(JSONObject data) {
        BigInteger newCarId = data.getJSONObject("car").getBigInteger("carId");
        Integer userId = data.getJSONObject("user").getInt("userId");
        CarId carId = new CarId();
        carId.setCarId(newCarId);
        carId.setUserId(userId);
        carIds.add(carId);
    }

    public List<CarId> getCarIds() {
        return carIds;
    }
}
