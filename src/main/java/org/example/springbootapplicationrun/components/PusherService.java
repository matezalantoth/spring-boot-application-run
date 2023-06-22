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
import org.example.springbootapplicationrun.components.containers.CarIdsContainer;
import org.example.springbootapplicationrun.components.containers.CarUserContainer;
import org.example.springbootapplicationrun.components.containers.GroupUserContainer;
import org.example.springbootapplicationrun.components.containers.QueueContainer;
import org.example.springbootapplicationrun.components.schedulers.DownloadMarketplaceCars;
import org.example.springbootapplicationrun.components.schedulers.GetGroupLinks;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PusherService {

    @Autowired
    DownloadMarketplaceCars downloadMarketplaceCars;
    @Autowired
    QueueContainer queueContainer;
    @Autowired
    GetGroupLinks getGroupLinks;
    @Autowired
    CarUserContainer carUserContainer;
    @Autowired
    GroupUserContainer groupUserContainer;
    @Autowired
    CarIdsContainer carIdsContainer;

    @Value("${pusher.api.key}")
    String apiKey;
    @Value("${computer.id}")
    Integer computerId;

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

                System.out.println("Received spring custom event - " + event.getData());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId){
                    return;
                }
                queueContainer.addPusherData(data);

            }
        });

        channel.bind("get-cars", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId){
                    return;
                }
                carUserContainer.addPusherData(data);
            }
        });

        channel.bind("get-datePosted", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId){
                    return;
                }
                carIdsContainer.addPusherData(data);

            }
        });

        channel.bind("get-groups", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId){
                    return;
                }
                groupUserContainer.addPusherData(data);

            }
        });

        pusher.disconnect();

    }

}
