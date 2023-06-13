package org.example.springbootapplicationrun.models;

import org.example.springbootapplicationrun.enums.GetGroupsStatus;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class GroupReport {

    private GetGroupsStatus status;
    private LocalDateTime statusChangedAt;

    public void setStatus(GetGroupsStatus status) {
        this.status = status;
    }
    public GetGroupsStatus getStatus() {
        return status;
    }
    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }
    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public JSONObject getGroupInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("statusChangedAt", statusChangedAt);
        return jsonObject;
    }
}
