package com.example.countriescoinapp.model;

import java.io.Serializable;

public class Country implements Serializable {

    private Long id;
    private String name;
    private String imageUrl;
    private String coin;

    public Country() {
    }

    public Country(Long id, String name, String imageUrl, String coin) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coin = coin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", coin='" + coin + '\'' +
                '}';
    }
}
