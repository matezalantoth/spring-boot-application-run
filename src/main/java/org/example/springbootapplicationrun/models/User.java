package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.UserStatus;

public class User {

    private String email;

    private String password;

    private Integer id;
    private UserStatus status;

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
    public boolean isValid() { return status == UserStatus.VALID; }

    public boolean isInvalid() {
        return status == UserStatus.INVALID;
    }



}
