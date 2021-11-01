package com.dev.objects;

public class Product {
    private String name;
    private int price;
    private String category;
    private String region;
    private String phone;
    private String imgLink;

    public Product(String name, int price, String category, String region, String phone, String imgLink) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.region = region;
        this.phone = phone;
        this.imgLink = imgLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
