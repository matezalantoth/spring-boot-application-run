package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.DataStatus;
import org.example.springbootapplicationrun.enums.PostStatus;
import org.json.JSONArray;
import org.json.JSONObject;

public class Data {

    private JSONObject user;
    private JSONObject post;
    private JSONArray group;
    private Integer userId;
    private Integer postId;
    private String groupName;
    private DataStatus status;

    public Data(){
        status = DataStatus.PROCESSING;
        group = new JSONArray();

    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    public DataStatus getStatus() {
        return status;
    }

    public boolean isProcessed() {
        return status == DataStatus.PROCESSED;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }
    public JSONObject getUser() {
        return user;
    }
    public void setPost(JSONObject post) {
        this.post = post;
    }
    public JSONObject getPost() {
        return post;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public Integer getPostId() {
        return postId;
    }


    public JSONArray getGroup() {
        return group;
    }

    public void setGroup(JSONArray group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
