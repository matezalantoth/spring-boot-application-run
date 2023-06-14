package org.example.springbootapplicationrun.models;

import org.json.JSONObject;

import java.math.BigInteger;

public class FinishedCar {
    private String postedAt;
    private String description;
    private BigInteger carId;

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public JSONObject getCarInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("carId", carId);
        jsonObject.put("description", description);
        jsonObject.put("postedAt", postedAt);
        return jsonObject;
    }
}
