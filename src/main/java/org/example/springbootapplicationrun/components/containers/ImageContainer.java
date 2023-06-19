package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.models.Image;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.LinkedHashMap;
@Component
public class ImageContainer {

    private LinkedHashMap<BigInteger, Image> images;

    public ImageContainer() {
        images = new LinkedHashMap<>();
    }

    public void addImage(Image image){

        BigInteger marketplaceId = image.getMarketplaceId();
        if (images.containsKey(marketplaceId)) {
            return;
        }
        images.put(marketplaceId, image);
        System.out.println("added");

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
