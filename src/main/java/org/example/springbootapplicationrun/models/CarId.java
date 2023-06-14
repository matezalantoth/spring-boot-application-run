package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.CarLinksStatus;

import java.math.BigInteger;

public class CarId {

    private BigInteger carId;
    private Integer userId;
    private CarLinksStatus status;

    public CarId(){
        status = CarLinksStatus.INIT;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CarLinksStatus getStatus() {
        return status;
    }

    public void setStatus(CarLinksStatus status) {
        this.status = status;
    }
}
