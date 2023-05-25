package org.example.springbootapplicationrun.components.clients;

import org.apache.commons.io.IOUtils;
import org.example.springbootapplicationrun.enums.UserStatus;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
@Component
public class EncryptedClient {

    public User getFbUser(Integer userId) throws IOException {
        URL webURL = new URL("loginprovider");
        String json = IOUtils.toString(webURL, Charset.forName("UTF-8"));
        JSONObject loginInfo = new JSONObject(json);
        String newEmail = loginInfo.getString("email");
        String newPassword = loginInfo.getString("password");
        User user = new User();
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setId(userId);
        user.setStatus(UserStatus.VALID);
        return user;
    }
}
