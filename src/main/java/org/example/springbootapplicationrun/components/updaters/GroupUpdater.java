package org.example.springbootapplicationrun.components.updaters;

import org.example.springbootapplicationrun.components.clients.GroupClient;
import org.example.springbootapplicationrun.components.clients.GroupLinksServer;
import org.example.springbootapplicationrun.enums.GetGroupsStatus;
import org.example.springbootapplicationrun.models.GroupInfo;
import org.example.springbootapplicationrun.models.GroupReport;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class GroupUpdater {

    @Autowired
    GroupClient groupClient;

    public void updateGroup(GroupInfo groupInfo, GetGroupsStatus status){
        LocalDateTime time = LocalDateTime.now();

        groupInfo.setStatus(status);
        groupInfo.setStatusChangedAt(time);

        GroupReport groupReport = new GroupReport();
        groupReport.setStatus(status);
        groupReport.setStatusChangedAt(time);

        JSONObject jsonObject = groupReport.getGroupInfo();
        groupClient.sendGroupStatusToServer(jsonObject);



    }

}
