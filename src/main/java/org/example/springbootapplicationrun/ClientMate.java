package org.example.springbootapplicationrun;

import models.Image;
import models.Post;
import models.User;
import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.components.FacebookBrowser;
import org.example.springbootapplicationrun.components.PostContainer;
import org.example.springbootapplicationrun.components.UserContainer;
import org.example.springbootapplicationrun.components.pages.GroupPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMate {


    @Scheduled(fixedRate = 60_000)
    public void downloadPosts() throws IOException{
        URL webURL = new URL("https://smp.ingatlanforras.hu/api/agencies/6913/facebook-posts");
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        JSONObject result = new JSONObject(json);
        JSONArray posts = result.getJSONArray("data");
        PostContainer postContainer = PostContainer.getInstance();
        UserContainer userContainer = UserContainer.getInstance();
        posts.forEach((post) -> {
            JSONObject posting = (JSONObject) post;
            Post poster = new Post();
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
            postContainer.addPost(poster);
        });
    }

    @Scheduled(fixedRate = 600_000, initialDelay = 10_000)
    public void sendSelectedPosts() throws InterruptedException {
        PostContainer postContainer = PostContainer.getInstance();
        List<Post> postering = postContainer.getPosts();

        FacebookBrowser facebookBrowser = FacebookBrowser.getInstance();
        UserContainer userContainer = UserContainer.getInstance();
        System.out.println(postering.size());

        for (Post post : postering) {
            User user = userContainer.getUserByUserId(post.getUserId());
            WebDriver driver = facebookBrowser.getBrowser(user.getEmail(), user.getPassword());
            System.out.println(post.getTitle());
            post.downloadImages();
            GroupPage groupPage = new GroupPage();
            groupPage.sendPost(post, driver);
            Thread.sleep(25000);
            driver.get("https://www.facebook.com");
            Thread.sleep(2000);

        }


    }


}



