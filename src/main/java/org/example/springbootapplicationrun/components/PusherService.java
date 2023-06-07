package org.example.springbootapplicationrun.components;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import jakarta.annotation.PostConstruct;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.schedulers.DownloadMarketplaceCars;
import org.example.springbootapplicationrun.components.schedulers.DownloadScheduledPost;
import org.example.springbootapplicationrun.components.schedulers.SendSelectedPosts;
import org.example.springbootapplicationrun.models.Post;
import org.example.springbootapplicationrun.models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class PusherService {

    @Autowired
    DownloadMarketplaceCars downloadMarketplaceCars;
    @Autowired
    DownloadScheduledPost downloadScheduledPost;
    @Autowired
    SendSelectedPosts sendSelectedPosts;
    @Autowired
    UserContainer userContainer;

    @Value("${pusher.api.key}")
    String apiKey;

    @PostConstruct
    public void init() {
        PusherOptions pusherOptions = new PusherOptions().setCluster("eu");
        Pusher pusher = new Pusher(apiKey, pusherOptions);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

        Channel channel = pusher.subscribe("main");

        channel.bind("get-posts", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {

                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                JSONObject userData = data.getJSONObject("user");
                JSONObject postData = data.getJSONObject("post");

                userContainer.addUser(userData);
                try {
                    downloadScheduledPost.downloadPost(postData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                sendSelectedPosts.sendSelectedPosts();


            }
        });

        channel.bind("get-cars", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {

                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer userId = data.getInt("userId");
                try {
                    downloadMarketplaceCars.downloadCars(userId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }


        });

        pusher.disconnect();

    }

}
