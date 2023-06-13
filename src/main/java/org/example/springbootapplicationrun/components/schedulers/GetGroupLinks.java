package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.updaters.UserUpdater;
import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.GroupLinksServer;
import org.example.springbootapplicationrun.components.containers.GroupUserContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.UserGroupPage;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGroupLinks {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private GroupLinksServer groupLinksServer;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private GroupUserContainer groupUserContainer;

@Scheduled(fixedRate = 60_000, initialDelay = 10_000)
    public void getLinks() throws Exception {
        List<User> users = groupUserContainer.getQueue();
        users.forEach(user -> {

            try {

                Integer userId = user.getId();

                User trueUser = userContainer.getFbUserByUserId(userId);

                WebDriver driver = facebookBrowser.getBrowser(trueUser);

                UserStatus currentStatus = user.getStatus();
                if (currentStatus == UserStatus.INVALID) {
                    return;
                }

                UserGroupPage userGroupPage = new UserGroupPage(driver);
                JSONArray links = userGroupPage.getGroups();
                groupLinksServer.sendLinksToServer(links);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
                facebookBrowser.closeBrowser(user);
            }
        });


    }


}
