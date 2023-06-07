package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.PostStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private String title;
    private String description;
    private List<Image> images;
    private Integer price;
    private String link;
    private Integer postId;
    private BigInteger facebookGroupId;
    private Integer userId;
    private PostStatus status;
    private LocalDateTime scheduledTo;

    public LocalDateTime getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(LocalDateTime scheduledTo) {
        this.scheduledTo = scheduledTo;
    }


    public Post() {
        status = PostStatus.PENDING;
    }


    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public PostStatus getStatus() {
        return status;
    }

    public boolean isDownloaded() {
        return status == PostStatus.DOWNLOADED;
    }

    public boolean isScheduled() {
        return status == PostStatus.SCHEDULED;
    }

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
            imageLinkBuilder.append(image.getLocalLink());
        });

        return (imageLinkBuilder.toString());

    }


}
