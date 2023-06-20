package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.containers.QueueContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.DataStatus;
import org.example.springbootapplicationrun.enums.GetPostStatus;
import org.example.springbootapplicationrun.models.Data;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Posting {

    @Autowired
    DownloadScheduledPost downloadScheduledPost;
    @Autowired
    SendSelectedPosts sendSelectedPosts;
    @Autowired
    UserContainer userContainer;
    @Autowired
    QueueContainer queueContainer;

    @Scheduled(fixedRate = 100_000, initialDelay = 10_000)
    public void downloadAndSendPosts() {
        System.out.println("started");
        List<Data> newDataList = queueContainer.getQueue();
        System.out.println("still-going");
        newDataList.forEach(data -> {
            DataStatus currentStatus = data.getStatus();

            if (currentStatus == DataStatus.PROCESSED){
                return;
            }

            JSONObject userData = data.getUser();
            JSONObject postData = data.getPost();
            Integer postId = postData.getInt("postId");

            Post post = new Post();
            post.setPostId(postId);
            try {

                userContainer.addUser(userData);
                downloadScheduledPost.downloadPost(post);
                sendSelectedPosts.sendSelectedPosts();

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
                post.setPusherStatus(GetPostStatus.FAILED);
                return;
            }
            post.setPusherStatus(GetPostStatus.FINISHED);
            data.setStatus(DataStatus.PROCESSED);

        });
//        newDataList = queueContainer.getQueue();
//        if (newDataList.size() >0){
//            downloadAndSendPosts();
//        }

    }

}
