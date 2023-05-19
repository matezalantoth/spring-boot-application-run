package org.example.springbootapplicationrun.models;

import java.time.LocalDateTime;

public class Token {

    private String authenticationToken;

    private LocalDateTime expiresAt;

    public void setAuthenticationToken(String authenticationToken){
        this.authenticationToken = authenticationToken;
    }

    public String getAuthenticationToken(){
        return authenticationToken;
    }
    public void setExpiresAt(LocalDateTime expiresAt){
        this.expiresAt = expiresAt;
    }
    public LocalDateTime getExpiresAt(){
        return expiresAt;
    }
}
