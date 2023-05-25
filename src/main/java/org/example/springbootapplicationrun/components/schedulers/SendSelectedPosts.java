package org.example.springbootapplicationrun.components.schedulers;


import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.GroupPostServer;
import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.components.containers.PostContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.PostReport;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.GroupPage;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class SendSelectedPosts {
    @Autowired
    private PostContainer postContainer;
    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private GroupPostServer groupPostServer;


    @Scheduled(fixedRate = 1_000_000, initialDelay = 10_000)
    public void sendSelectedPosts() {
        postContainer.schedulePosts();
        List<Post> postering = postContainer.getPosts();
        System.out.println(postering.size());
        for (Post post : postering) {
            try {

                post.setStatus(PostStatus.POSTING);
                User user = userContainer.getFbUserByUserId(post.getUserId());
                WebDriver driver = facebookBrowser.getBrowser(user.getEmail(), user.getPassword(), user.getStatus(), user.getId());

                UserStatus currentStatus = user.getStatus();
                if (currentStatus == UserStatus.INVALID) {
                    return;
                }

                System.out.println(currentStatus);

                UserReport userReport = new UserReport();
                UserClient userStatusServer = new UserClient();

                System.out.println(currentStatus);
                userReport.setUserStatus(user.getStatus());
                userReport.setId(user.getId());

                JSONObject userStatus = userReport.getUserStatusJSON();
                userStatusServer.sendUserInfoToServer(userStatus);

                System.out.println(post.getTitle());
                post.downloadImages();

                try {

                    GroupPage groupPage = new GroupPage();
                    groupPage.sendPost(post, driver);

                }catch (Exception e){
                    user.isInvalid();
                }
                user.isValid();

                post.setStatus(PostStatus.POSTED);
                LocalDateTime time = LocalDateTime.from(LocalTime.now());

                PostReport postReport = new PostReport();
                postReport.setPostedAt(time);
                postReport.setPostId(post.getPostId());
                postReport.setPostStatus(post.getStatus());

                JSONObject jsonObject = postReport.getPostInfo();
                groupPostServer.sendPostReportsToServer(jsonObject);
                Thread.sleep(25000);
                driver.get("https://www.facebook.com");
                Thread.sleep(2000);

            }catch (Exception e){
                post.setStatus(PostStatus.FAILED);
            }
        }
    }
}
