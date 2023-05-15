package org.example.springbootapplicationrun.models;

import org.json.JSONObject;

public final class Car {
    private String title;
    private String image;
    private String distance;
    private String price;
    private String link;

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

    public JSONObject setJSONInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("image", image);
        jsonObject.put("distance", distance);
        jsonObject.put("price", price);
        jsonObject.put("link", link);

        return jsonObject;
    }

    public JSONObject getJSONInfo() {

        return setJSONInfo();
    }

    public String[] toArray() {
        String[] myStrings = {this.link, this.price, this.distance, this.image, this.title};
        return myStrings;
    }


}
