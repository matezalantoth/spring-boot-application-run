package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.models.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class GroupUserContainer {
    private List<User> groupUserData;

    public GroupUserContainer(){
        this.groupUserData = new ArrayList<>();
    }

    public void addPusherData(JSONObject data) {
        User user = new User();
        Integer userId = data.getJSONObject("user").getInt("userId");
        user.setId(userId);
        groupUserData.add(user);

    }



    public List<User> getQueue() {

        return groupUserData;
    }

}
