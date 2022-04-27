package dto;

import org.bson.types.ObjectId;

public class ListingDto {

    private ObjectId listingId;
    private String type;
    private Integer price;
    private String title;

    public ListingDto() {
    }

    public ListingDto(String type, Integer price, String title) {
        this.listingId = null;
        this.type = type;
        this.price = price;
        this.title = title;
    }

    public ObjectId getId() {
        return listingId;
    }

    public void setId(ObjectId id) {
        this.listingId = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}