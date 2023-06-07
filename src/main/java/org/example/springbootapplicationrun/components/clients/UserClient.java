package org.example.springbootapplicationrun.components.clients;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    public void sendUserInfoToServer(JSONObject userReport) {
        System.out.println(userReport);
//        WebClient.create("users.thesoftwareadvisor.co.uk/userStatus")
//                .post()
//                .body(Mono.just(userReport), JSONObject.class)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve();

    }
}
