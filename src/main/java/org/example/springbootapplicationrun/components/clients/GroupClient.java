package org.example.springbootapplicationrun.components.clients;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class GroupClient {
    public void sendGroupStatusToServer(JSONObject groupInfo) {
        WebClient.create("groups.thesoftwareadvisor.co.uk/groupreports")
                .post()
                .body(Mono.just(groupInfo), JSONObject.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

    }
}
