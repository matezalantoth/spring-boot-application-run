package org.example.springbootapplicationrun.models;

import org.apache.commons.codec.binary.Base64;
import org.example.springbootapplicationrun.enums.ImageStatus;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;

import static org.apache.commons.io.IOUtils.toByteArray;

public class Image {

    @Value("${image.path}")
    String basePath;

    private String url;
    private String localLink;
    private Integer postId;
    private BigInteger marketplaceId;
    private String imageContent;
    private ImageStatus status;

    public Image(){
        status = ImageStatus.NOT_DOWNLOADED;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl(){
        return url;
    }

    public void setLocalLink(String localLink) {
        this.localLink = localLink;
    }

    public String getLocalLink() {
        return localLink;
    }

    public void download() throws InterruptedException {

        try {
            URL webUrl = new URL(url);

            BufferedImage newImage = ImageIO.read(webUrl);

            Thread.sleep(2000);
            String time = String.valueOf(Instant.now().getEpochSecond());

            final String path = "/home/matezalantoth/Downloads/" + postId;

            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }

            localLink = path + "/" + time + ".jpg";
            ImageIO.write(newImage, "jpg", new File(localLink));

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public ImageStatus getStatus() {
        return status;
    }

    public void setStatus(ImageStatus status) {
        this.status = status;
    }

//    public String getImageContent() throws IOException {
//
//         String imageDataString = "data:image/png;base64," + getBase64EncodedImage() ;
//        return imageDataString;
//
//    }
//
//    public String getBase64EncodedImage() throws IOException {
//        URL webUrl = new URL(url);
//        InputStream is = webUrl.openStream();
//        byte[] bytes = toByteArray(is);
//        return Base64.encodeBase64String(bytes);
//    }
//
//    public ImageStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(ImageStatus status) {
//        this.status = status;
//    }
}


