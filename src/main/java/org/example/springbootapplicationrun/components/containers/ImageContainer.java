package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.enums.ImageStatus;
import org.example.springbootapplicationrun.models.Image;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
@Component
public class ImageContainer {

    private LinkedHashMap<String, Image> images;

    public ImageContainer(){
        this.images = new LinkedHashMap<>();
    }

    public void addImage(Image image){
        image.setStatus(ImageStatus.DOWNLOADED);
        images.put(image.getUrl(), image);
    }

    public LinkedHashMap<String, Image> getImages(){
        return images;
    }

    public boolean doesImageExist(String url){
        images.get(url);
        return true;
    }

    public Image getImageByURL(String url){

        Image image = images.get(url);
        if (image != null){
            return image;
        }
        image = new Image();
        image.setUrl(url);

        return image;
    }


}
