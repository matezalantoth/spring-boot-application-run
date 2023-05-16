package org.example.springbootapplicationrun.components;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class GroupPostServer {

    public void sendPostReportsToServer(JSONObject postReport){
        WebClient.create("posts.thesoftwareadvisor.co.uk/postreports")
                .post()
                .body(Mono.just(postReport), JSONObject.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

    }
}
