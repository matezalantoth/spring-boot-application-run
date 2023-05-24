package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.GroupLinksServer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.UserGroupPage;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetGroupLinks {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private GroupLinksServer groupLinksServer;

    @Scheduled (fixedRate = 3600000)
    public void getLinks() throws IOException, InterruptedException {

        User user = userContainer.getFbUserByUserId(1);

        WebDriver driver = facebookBrowser.getBrowser(user.getEmail(), user.getPassword());

        UserGroupPage userGroupPage = new UserGroupPage(driver);
        JSONArray links = userGroupPage.getGroups();
        groupLinksServer.sendLinksToServer(links);

    }


}
