package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.enums.ImageStatus;
import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Image;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.LinkedHashMap;
@Component
public class ImageContainer {

    private LinkedHashMap<BigInteger, Image> images;

    public ImageContainer(){
        this.images = new LinkedHashMap<>();
    }

    public void addImage(Image image){
        images.put(image.getMarketplaceId(), image);
    }

    public LinkedHashMap<BigInteger, Image> getImages(){
        return images;
    }

    public boolean doesExist(BigInteger marketplaceId){
        return images.containsKey(marketplaceId);
    }


    public Image getImageByMarketplaceId(BigInteger marketplaceId){

        Image image = images.get(marketplaceId);
        if (image != null){
            return image;
        }
        image = new Image();
        image.setMarketplaceId(marketplaceId);

        return image;
    }


}
