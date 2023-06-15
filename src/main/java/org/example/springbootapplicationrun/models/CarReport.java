package org.example.springbootapplicationrun.models;

import org.json.JSONObject;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class CarReport {

    private BigInteger carId;
    private String imageAsString;
    private LocalDateTime completedAt;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getImageAsString() {
        return imageAsString;
    }

    public void setImageAsString(String imageAsString) {
        this.imageAsString = imageAsString;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public JSONObject getGroupInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("carId", carId);
        jsonObject.put("imageAsString", imageAsString);
        jsonObject.put("completedAt", completedAt);
        return jsonObject;
    }
}
