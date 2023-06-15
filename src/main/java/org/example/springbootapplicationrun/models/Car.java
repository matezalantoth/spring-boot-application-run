package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.json.JSONObject;

import java.math.BigInteger;

public final class Car {


    private String title;
    private String image;
    private String distance;
    private String price;


    private String link;
    private GetCarsStatus carsStatus;
    private Integer userId;
    private BigInteger marketplaceId;
    private String imageContent;
    private String postedAt;
    private String description;


    public Car() {
        carsStatus = GetCarsStatus.INIT;

    }

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }


    public String getImage() {
        return image;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
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
        jsonObject.put("description", description);
        jsonObject.put("image", image);
        jsonObject.put("distance", distance);
        jsonObject.put("price", price);
        jsonObject.put("link", link);
        jsonObject.put("marketplaceId", marketplaceId);
        jsonObject.put("imageContent", imageContent);
        jsonObject.put("postedAt", postedAt);

        return jsonObject;
    }

    public String[] toArray() {
        String[] myStrings = {this.link, this.price, this.distance, this.image, this.title};
        return myStrings;
    }


    public BigInteger getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(BigInteger marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

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
}
