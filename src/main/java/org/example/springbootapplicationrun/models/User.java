package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.UserStatus;

import java.time.LocalDateTime;

public class User {

    private String email;
    private String password;
    private Integer id;
    private UserStatus status;
    private LocalDateTime statusChangedAt;

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }



    public User(){
        setStatus(UserStatus.VALID);
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

    public boolean isValid(){
        return status == UserStatus.VALID;
    }
    public boolean isInvalid(){
        return status == UserStatus.INVALID;
    }




}
