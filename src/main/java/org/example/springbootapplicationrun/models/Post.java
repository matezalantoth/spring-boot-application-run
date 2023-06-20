package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.GetPostStatus;
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
    private GetPostStatus pusherStatus;
    private LocalDateTime scheduledTo;
    private LocalDateTime statusChangedAt;
    private Integer roS;
    private Integer toH;
    private Integer wMoD;
    private Integer aCT;
    private Integer hT;
    private Integer pF;
    private Integer pT;
    private String bed;
    private String bath;
    private String propSize;
    private String availAt;
    private String address;


    public Post() {
        status = PostStatus.PENDING;
        pusherStatus = GetPostStatus.INIT;
        roS = 0;
        toH = 0;
        wMoD = 0;
        aCT = 0;
        hT = 0;
        pF = 0;
        pT = 0;
        bed = "3";
        bath = "1.5";
        propSize = "10,000";
        availAt = "6 9 2023";
        address = "Szeged 6722";
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setPusherStatus(GetPostStatus pusherStatus) {
        this.pusherStatus = pusherStatus;
    }

    public GetPostStatus getPusherStatus() {
        return pusherStatus;
    }

    public LocalDateTime getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(LocalDateTime scheduledTo) {
        this.scheduledTo = scheduledTo;
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


    public Integer getRoS() {
        return roS;
    }

    public void setRoS(Integer roS) {
        this.roS = roS;
    }

    public Integer getToH() {
        return toH;
    }

    public void setToH(Integer toH) {
        this.toH = toH;
    }

    public Integer getWMoD() {
        return wMoD;
    }

    public void setWMoD(Integer wMoD) {
        this.wMoD = wMoD;
    }

    public Integer getACT() {
        return aCT;
    }

    public void setACT(Integer aCT) {
        this.aCT = aCT;
    }

    public Integer getHT() {
        return hT;
    }

    public void setHT(Integer hT) {
        this.hT = hT;
    }

    public Integer getPF() {
        return pF;
    }

    public void setPF(Integer pF) {
        this.pF = pF;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getPropSize() {
        return propSize;
    }

    public void setPropSize(String propSize) {
        this.propSize = propSize;
    }

    public String getAvailAt() {
        return availAt;
    }

    public void setAvailAt(String availAt) {
        this.availAt = availAt;
    }

    public Integer getpT() {
        return pT;
    }

    public void setpT(Integer pT) {
        this.pT = pT;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
