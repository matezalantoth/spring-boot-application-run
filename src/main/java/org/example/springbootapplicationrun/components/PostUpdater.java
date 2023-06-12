package org.example.springbootapplicationrun.components;

import org.example.springbootapplicationrun.components.clients.PostClient;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.PostReport;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Component
public class PostUpdater {

    @Autowired
    PostClient postClient;

    public void updatePost(Post post, PostStatus postStatus){

        LocalDateTime time = LocalDateTime.now();

        post.setStatus(postStatus);
        post.setStatusChangedAt(time);

        PostReport postReport = new PostReport();

        postReport.setPostedAt(time);
        postReport.setPostId(post.getPostId());
        postReport.setPostStatus(post.getStatus());

        JSONObject jsonObject = postReport.getPostInfo();
        postClient.sendPostReportsToServer(jsonObject);

    }

}
