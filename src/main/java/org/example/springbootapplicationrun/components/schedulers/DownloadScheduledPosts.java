package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.clients.PostClient;
import org.example.springbootapplicationrun.components.containers.PostContainer;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.models.Image;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownloadScheduledPosts {
    @Autowired
    private PostContainer postContainer;
    @Autowired
    private PostClient postClient;

    @Scheduled(fixedRate = 120_000)
    public void downloadPosts() throws Exception {

        JSONObject result = postClient.getPosts();
        JSONArray posts = result.getJSONArray("data");
        posts.forEach((post) -> {

            System.out.println(post);
            JSONObject posting = (JSONObject) post;

            Post poster = new Post();
            Integer postId = (posting.getInt("id"));

            if (postContainer.doesPostExist(postId)) {
                return;
            }

            poster.setScheduledTo(LocalDateTime.parse(posting.getString("scheduledTo"), DateTimeFormatter.ISO_DATE_TIME));

            BigInteger facebookGroupId = (posting.getJSONObject("facebook_group").getBigInteger("groupId"));
            poster.setFacebookGroupId(facebookGroupId);
            System.out.println(facebookGroupId);

            poster.setPostId(postId);

            Integer userId = (posting.getInt("userId"));
            poster.setUserId(userId);

            poster.setTitle(posting.getJSONObject("lead").getString("title"));
            poster.setDescription(posting.getJSONObject("lead").getString("description"));
            poster.setPrice(posting.getJSONObject("lead").getInt("price"));

            int linkaddress = (posting.getJSONObject("lead").getInt("leadId"));
            Integer.toString(linkaddress);
            poster.setLink(String.valueOf(linkaddress));

            List<Image> imaging = new ArrayList<>();
            JSONArray images = posting.getJSONObject("lead").getJSONArray("images");
            images.forEach(image -> {
                JSONObject imaginging = (JSONObject) image;

                Image newImage = new Image();
                newImage.setUrl(imaginging.getString("url"));
                newImage.setPostId(poster.getPostId());

                try {
                    newImage.download();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                imaging.add(newImage);
            });
            poster.setImages(imaging);
            poster.setStatus(PostStatus.DOWNLOADED);
            postContainer.addPost(poster);

        });

    }
}




