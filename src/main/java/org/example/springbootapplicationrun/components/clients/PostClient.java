package org.example.springbootapplicationrun.components.clients;

import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Component
public class PostClient {

    public JSONObject getPosts() throws IOException {
        URL webURL = new URL("https://smp.ingatlanforras.hu/api/agencies/6913/facebook-posts");
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        System.out.println(json);
        JSONObject result = new JSONObject(json);
        return result;
    }
}
