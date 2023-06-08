package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.json.JSONObject;

public final class Car {
    private String title;
    private String image;
    private String distance;
    private String price;
    private String link;
    private GetCarsStatus carsStatus;

    public Car(){
        carsStatus = GetCarsStatus.INIT;

    }

    public void setCarsStatus(GetCarsStatus carsStatus) {
        this.carsStatus = carsStatus;
    }
    public GetCarsStatus getCarsStatus() {
        return carsStatus;
    }

    public void setTitle(String title) {

        this.title = title;
    }


    public void setImage(String image) {

        this.image = image;
    }

    public void setDistance(String distance) {

        this.distance = distance;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public void setLink(String link) {

        this.link = link;
    }

    public JSONObject getJSONInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("image", image);
        jsonObject.put("distance", distance);
        jsonObject.put("price", price);
        jsonObject.put("link", link);

        return jsonObject;
    }

    public String[] toArray() {
        String[] myStrings = {this.link, this.price, this.distance, this.image, this.title};
        return myStrings;
    }


}
