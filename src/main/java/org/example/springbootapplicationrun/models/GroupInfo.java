package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.GetGroupsStatus;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class GroupInfo {

    String link;

    String name;

    String image;

    GetGroupsStatus status;
    LocalDateTime statusChangedAt;

    public GroupInfo(){
        status = GetGroupsStatus.INIT;
    }
    public void setStatus(GetGroupsStatus status) {

        this.status = status;
    }
    public void setStatusChangedAt(LocalDateTime statusChangedAt) {

        this.statusChangedAt = statusChangedAt;
    }
    public LocalDateTime getStatusChangedAt(){
        return statusChangedAt;
    }
    public GetGroupsStatus getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public JSONObject getJSONGroupInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("link", link);
        jsonObject.put("name", name);
        jsonObject.put("image", image);
        return jsonObject;
    }


}
