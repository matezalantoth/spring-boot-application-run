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
import org.example.springbootapplicationrun.components.containers.QueueContainer;
import org.example.springbootapplicationrun.components.containers.UserContainer;
import org.example.springbootapplicationrun.components.schedulers.DownloadMarketplaceCars;
import org.example.springbootapplicationrun.components.schedulers.DownloadScheduledPost;
import org.example.springbootapplicationrun.components.schedulers.GetGroupLinks;
import org.example.springbootapplicationrun.components.schedulers.SendSelectedPosts;
import org.example.springbootapplicationrun.enums.GetCarsStatus;
import org.example.springbootapplicationrun.enums.GetGroupsStatus;
import org.example.springbootapplicationrun.enums.GetPostStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Data;
import org.example.springbootapplicationrun.models.GroupInfo;
import org.example.springbootapplicationrun.models.Post;
import org.json.JSONArray;
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

                System.out.println("Received spring custom event - " + event.getData());
                System.out.println("Received event with data: " + event.toString());
                JSONObject jsonObject = new JSONObject(event.getData());
                queueContainer.addPusherData(jsonObject);

            }
        });

        channel.bind("get-cars", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {

                Car car = new Car();

                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer userId = data.getInt("userId");
                try {
                    downloadMarketplaceCars.downloadCars(userId, car);

                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    car.setCarsStatus(GetCarsStatus.FAILED);
                    throw new RuntimeException(e);
                }
                car.setCarsStatus(GetCarsStatus.FINISHED);
                String status = String.valueOf(car.getCarsStatus());
                System.out.println(status);

            }


        });

        channel.bind("get-groups", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
                JSONObject data = new JSONObject(event.getData());
                Integer userId = data.getInt("userId");
                GroupInfo groupInfo = new GroupInfo();
                try {
                    getGroupLinks.getLinks(userId, groupInfo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                groupInfo.setStatus(GetGroupsStatus.FINISHED);
                String status = String.valueOf(groupInfo.getStatus());
                System.out.println(status);

            }
        });

        pusher.disconnect();

    }

}
