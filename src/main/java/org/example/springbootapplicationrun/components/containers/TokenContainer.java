package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.models.Token;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenContainer {

    private Token token;

    public String getToken() {

        return token.getAuthenticationToken();
    }

    public LocalDateTime getExpiresAt() {
        return token.getExpiresAt();
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
