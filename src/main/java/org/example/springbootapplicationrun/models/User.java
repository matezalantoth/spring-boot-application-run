package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.UserStatus;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String password;
    private Integer id;
    private UserStatus status;
    private LocalDateTime statusChangedAt;
    private List<GroupInfo> groups;


    public User() {
        setStatus(UserStatus.VALID);
        this.groups = new ArrayList<>();
    }

    public JSONObject getUserData() {
        return userData;
    }

    public void setUserData(JSONObject userData) {
        this.userData = userData;
    }

    private JSONObject userData;

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public boolean isValid() {
        return status == UserStatus.VALID;
    }

    public boolean isInvalid() {
        return status == UserStatus.INVALID;
    }

    public boolean isUnderReview() {
        return status == UserStatus.UNDER_REVIEW;
    }


}
