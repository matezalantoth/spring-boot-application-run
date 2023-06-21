package org.example.springbootapplicationrun.components.clients;

import org.example.springbootapplicationrun.models.GroupInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GroupLinksServer {

    public void sendLinksToServer(List<GroupInfo> groups) {
        JSONArray linksJSON = new JSONArray();
        groups.forEach(groupInfo -> {
            JSONObject groupData = groupInfo.getJSONGroupInfo();
            linksJSON.put(groupData);
        });
        WebClient.create("groups.thesoftwareadvisor.co.uk/links")
                .post()
                .body(Mono.just(linksJSON), JSONArray.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
