package com.example.mikolaj.sklepallegro;

public class Offer {
    private String id;
    private String name;
    private String thumbnailUrl;
    private Price price;
    private String description;

    public Offer(String id, String name, String thumbnailUrl, double amount, String currency, String description){
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.price = new Price(amount, currency);
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Price getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
