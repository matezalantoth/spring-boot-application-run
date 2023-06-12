package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.PostUpdater;
import org.example.springbootapplicationrun.components.clients.PostClient;
import org.example.springbootapplicationrun.components.containers.PostContainer;
import org.example.springbootapplicationrun.enums.GetPostStatus;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.models.Image;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownloadScheduledPost {

    @Autowired
    private PostContainer postContainer;
    @Autowired
    private PostClient postClient;
    @Autowired
    private PostUpdater postUpdater;

    public void downloadPost(Post poster) throws Exception {

        JSONObject postData = postClient.getPost(poster.getPostId());

        poster.setPusherStatus(GetPostStatus.IN_PROGRESS);
        poster.setScheduledTo(LocalDateTime.parse(postData.getString("scheduledTo"), DateTimeFormatter.ISO_DATE_TIME));
        poster.setFacebookGroupId(postData.getJSONObject("facebook_group").getBigInteger("groupId"));
        poster.setUserId((postData.getInt("userId")));
        poster.setTitle(postData.getJSONObject("lead").getString("title"));
        poster.setDescription(postData.getJSONObject("lead").getString("description"));
        poster.setPrice(postData.getJSONObject("lead").getInt("price"));

        int linkaddress = (postData.getJSONObject("lead").getInt("leadId"));
        Integer.toString(linkaddress);
        poster.setLink(String.valueOf(linkaddress));

        List<Image> imageList = new ArrayList<>();
        JSONArray images = postData.getJSONObject("lead").getJSONArray("images");

        images.forEach(image -> {
            JSONObject imaginging = (JSONObject) image;

            Image nextImage = new Image();
            nextImage.setUrl(imaginging.getString("url"));
            nextImage.setPostId(poster.getPostId());

            try {
                nextImage.download();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            imageList.add(nextImage);
        });

        poster.setImages(imageList);

        postUpdater.updatePost(poster, PostStatus.DOWNLOADED);
        postContainer.addPost(poster);





    }
}





