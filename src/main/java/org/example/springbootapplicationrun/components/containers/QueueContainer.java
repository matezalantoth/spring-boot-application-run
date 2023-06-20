package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.enums.DataStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class QueueContainer {

    private List<Data> queueData;

    public QueueContainer(){

        this.queueData = new ArrayList<>();
    }


    public void addPusherData(JSONObject data) {
        Data newData = new Data();
        JSONObject userData = data.getJSONObject("user");
        JSONObject postData = data.getJSONObject("post");
        newData.setUser(userData);
        newData.setPost(postData);
        queueData.add(newData);
        queueData.forEach(queueDataEntry->{
            DataStatus currentStatus = queueDataEntry.getStatus();
            if (currentStatus == DataStatus.PROCESSED){
                queueData.remove(queueDataEntry);
            }
        });

    }

    public List<Data> getQueue() {

        return queueData;
    }
}
