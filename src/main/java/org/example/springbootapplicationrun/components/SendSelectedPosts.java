package org.example.springbootapplicationrun.components;


import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.components.pages.GroupPage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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



    @Scheduled(fixedRate = 1_000_000, initialDelay = 10_000)
    public void sendSelectedPosts() throws InterruptedException {
        List<Post> postering = postContainer.getPosts();
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
