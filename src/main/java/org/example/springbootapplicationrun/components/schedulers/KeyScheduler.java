package org.example.springbootapplicationrun.components.schedulers;

import org.example.springbootapplicationrun.components.containers.TokenContainer;
import org.example.springbootapplicationrun.components.clients.KeyClient;
import org.example.springbootapplicationrun.models.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

@Component
public class KeyScheduler {
    @Autowired private TokenContainer tokenContainer;
    @Autowired private KeyClient keyClient;
    @Scheduled(fixedRate = 120_000)
    public void checkIfExpired() {
        LocalDateTime tokenExpiresAt = tokenContainer.getExpiresAt();
        if (tokenExpiresAt.isAfter(ChronoLocalDateTime.from(LocalTime.now()))) {
            return;
        }
        try {
            Token currentToken = keyClient.getCurrentToken();
            tokenContainer.setToken(currentToken);

        }catch (Exception e){
            System.out.println("got you");
        }
    }
}
