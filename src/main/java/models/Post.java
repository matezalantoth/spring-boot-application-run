package models;

import java.math.BigInteger;
import java.util.List;

public class Post {
    String title;
    String description;
    List<Image> images;
    Integer price;
    String link;
    Integer postId;
    BigInteger facebookGroupId;

    Integer userId;

    public void setFacebookGroupId(BigInteger facebookGroupId) {
        this.facebookGroupId = facebookGroupId;
    }

    public BigInteger getFacebookGroupId() {
        return facebookGroupId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImages(List<Image> imagesList) {
        this.images = imagesList;
    }

    public void setPrice(Integer price) {
        this.price = price;

    }

    public Integer getPrice() {

        return price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return (link);
    }


    public void downloadImages() throws InterruptedException {

        images.forEach(image -> {
            try {
                image.download();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String getImageLocation() {

        final StringBuilder imageLinkBuilder = new StringBuilder();

        images.forEach(image -> {
            if (!imageLinkBuilder.isEmpty()) {
                imageLinkBuilder.append("\n");
            }
            imageLinkBuilder.append(image.localLink);
        });

        return (imageLinkBuilder.toString());

    }


}
