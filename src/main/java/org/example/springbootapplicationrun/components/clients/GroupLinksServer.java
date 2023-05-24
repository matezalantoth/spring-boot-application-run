package org.example.springbootapplicationrun.components.clients;

import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GroupLinksServer {

    public void sendLinksToServer(JSONArray linksJSON){
        WebClient.create("groups.thesoftwareadvisor.co.uk/links")
                .post()
                .body(Mono.just(linksJSON), JSONArray.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
