package org.example.springbootapplicationrun.components.schedulers;


import org.example.springbootapplicationrun.components.updaters.PostUpdater;
import org.example.springbootapplicationrun.components.updaters.UserUpdater;
import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.containers.PostContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.GroupPage;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.User;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private UserUpdater userUpdater;
    @Autowired
    private PostUpdater postUpdater;

    public void sendSelectedPosts() {
        List<Post> posts = postContainer.getPosts();
        System.out.println(posts.size());
        for (Post post : posts) {

            if (!post.isDownloaded()){
                continue;
            }
            try {
                postUpdater.updatePost(post, PostStatus.POSTING);
                User user = userContainer.getFbUserByUserId(post.getUserId());

                if (!userContainer.canPost(user)){
                    continue;
                }

                userUpdater.updateStatus(user, UserStatus.IN_USE);
                WebDriver driver = facebookBrowser.getBrowser(user);


                try {

                    GroupPage groupPage = new GroupPage();
                    groupPage.sendPost(post, driver);

                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
                    throw e;
                }
                userUpdater.updateStatus(user, UserStatus.NOT_IN_USE);
                postUpdater.updatePost(post, PostStatus.POSTED);



            } catch (Exception e) {
                postUpdater.updatePost(post, PostStatus.FAILED);
            }

        }
        if (postContainer.getPosts().size() > 0){
            sendSelectedPosts();
        }
    }
}
