package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.PostStatus;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class PostReport {
    private LocalDateTime postedAt;
    private Integer postId;
    private PostStatus postStatus;

    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostStatus(PostStatus postStatus){
        this.postStatus = postStatus;
    }

    public PostStatus getPostStatus(){
        return postStatus;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public JSONObject getPostInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("postedAt", postedAt);
        jsonObject.put("postId", postId);
        jsonObject.put("postStatus", postStatus.name());
        return jsonObject;
    }






}

