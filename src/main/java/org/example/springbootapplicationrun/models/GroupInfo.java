package org.example.springbootapplicationrun.models;

import org.json.JSONObject;

public class GroupInfo {

    String link;

    String name;

    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public JSONObject getJSONGroupInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("link", link);
        jsonObject.put("name", name);
        jsonObject.put("image", image);
        return jsonObject;
    }



}
