package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.json.JSONObject;

public class UserReport {

    private UserStatus userStatus;

    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }






    public JSONObject getUserStatusJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userStatus", userStatus);
        jsonObject.put("id", id);
        return jsonObject;
    }




}
