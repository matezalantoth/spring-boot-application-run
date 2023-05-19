package org.example.springbootapplicationrun.components.clients;

import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.models.Token;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class KeyClient {

    public Token getCurrentToken() throws IOException {
        URL webURL = new URL("keyprovider");
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        JSONObject keyData = new JSONObject(json);
        String newToken = keyData.getString("token");
        Token token = new Token();
        token.setAuthenticationToken(newToken);
        token.setExpiresAt(LocalDateTime.parse(keyData.getString("scheduledTo"), DateTimeFormatter.ISO_DATE_TIME));
        return token;
    }

}
