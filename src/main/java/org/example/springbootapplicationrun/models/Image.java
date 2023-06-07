package org.example.springbootapplicationrun.models;

import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

public class Image {

    @Value("${image.path}")
    String basePath;

    private String url;
    private String localLink;
    private Integer postId;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setUrl(String url) {
        this.url = url;
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

            final String path = "images/" + postId;

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

}


