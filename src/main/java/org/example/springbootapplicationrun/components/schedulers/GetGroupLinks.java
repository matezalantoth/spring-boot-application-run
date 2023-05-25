package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.browsers.FacebookBrowser;
import org.example.springbootapplicationrun.components.clients.GroupLinksServer;
import org.example.springbootapplicationrun.components.clients.UserClient;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.pages.UserGroupPage;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.example.springbootapplicationrun.models.UserReport;
import org.json.JSONArray;
import org.json.JSONObject;
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

        WebDriver driver = facebookBrowser.getBrowser(user);

        UserStatus currentStatus = user.getStatus();
        if (currentStatus == UserStatus.INVALID){
            return;
        }
        try {

            UserGroupPage userGroupPage = new UserGroupPage(driver);
            JSONArray links = userGroupPage.getGroups();
            groupLinksServer.sendLinksToServer(links);

        }catch(Exception e){

            user.setStatus(UserStatus.INVALID);
        }

        System.out.println(currentStatus);

        UserReport userReport = new UserReport();
        UserClient userStatusServer = new UserClient();

        System.out.println(currentStatus);
        userReport.setUserStatus(user.getStatus());
        userReport.setId(user.getId());

        JSONObject userStatus = userReport.getUserStatusJSON();
        userStatusServer.sendUserInfoToServer(userStatus);

    }


}
