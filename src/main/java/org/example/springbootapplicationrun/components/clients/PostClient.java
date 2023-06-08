package org.example.springbootapplicationrun.components.clients;

import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Component
public class PostClient {

    public JSONObject getPost(Integer postId) throws IOException {
        URL webURL = new URL("https://smp.ingatlanforras.hu/api/agencies/6913/facebook-posts/"+ postId);
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        System.out.println(json);
        JSONObject result = new JSONObject(json);
        return result;
    }

    public void sendPostReportsToServer(JSONObject postReport) {
        WebClient.create("posts.thesoftwareadvisor.co.uk/postreports")
                .post()
                .body(Mono.just(postReport), JSONObject.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

    }
}
