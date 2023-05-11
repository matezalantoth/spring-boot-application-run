package models;

public final class Car {
    String title;
    String image;
    String distance;
    String price;
    String link;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String[] toArray() {
        String[] myStrings = {this.link, this.price, this.distance, this.image, this.title};
        return myStrings;
    }


}
