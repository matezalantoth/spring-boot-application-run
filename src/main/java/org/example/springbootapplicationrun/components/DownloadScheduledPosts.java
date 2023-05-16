package org.example.springbootapplicationrun.components;

import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.models.Image;
import org.example.springbootapplicationrun.models.Post;
import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.models.PostReport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownloadScheduledPosts {

    @Autowired
    private PostContainer postContainer;
    @Autowired
    private UserContainer userContainer;


    @Scheduled(fixedRate = 60_000)
    public void downloadPosts() throws IOException{
        URL webURL = new URL("https://smp.ingatlanforras.hu/api/agencies/6913/facebook-posts");
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        System.out.println(json);
        JSONObject result = new JSONObject(json);
        JSONArray posts = result.getJSONArray("data");
        posts.forEach((post) -> {
            System.out.println(post);
            JSONObject posting = (JSONObject) post;
            Post poster = new Post();
            poster.setScheduledTo(LocalDateTime.parse(posting.getString("scheduledTo"), DateTimeFormatter.ISO_DATE_TIME));
            BigInteger facebookGroupId = (posting.getJSONObject("facebook_group").getBigInteger("groupId"));
            poster.setFacebookGroupId(facebookGroupId);
            Integer postId = (posting.getInt("id"));
            poster.setPostId(postId);
            Integer userId = (posting.getInt("userId"));
            poster.setUserId(userId);
            System.out.println(facebookGroupId);
            List<Image> imaging = new ArrayList<>();
            poster.setTitle(posting.getJSONObject("lead").getString("title"));
            poster.setDescription(posting.getJSONObject("lead").getString("description"));
            poster.setPrice(posting.getJSONObject("lead").getInt("price"));
            int linkaddress = (posting.getJSONObject("lead").getInt("leadId"));
            Integer.toString(linkaddress);
            poster.setLink(String.valueOf(linkaddress));
            JSONArray images = posting.getJSONObject("lead").getJSONArray("images");
            images.forEach(image -> {
                JSONObject imaginging = (JSONObject) image;
                Image newImage = new Image();
                newImage.setUrl(imaginging.getString("url"));
                imaging.add(newImage);
            });
            poster.setImages(imaging);
            poster.setStatus(PostStatus.DOWNLOADED);
            postContainer.addPost(poster);
        });
    }



}



