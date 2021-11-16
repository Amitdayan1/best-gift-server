package com.dev.objects;

public class Product {
    private String name;
    private int price;
    private String category;
    private String region;
    private String phone;
    private String imgLink;
    private String uniqId;
    private boolean isSelected;

    public Product(String name, int price, String category, String region, String phone, String imgLink,String uniqId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.region = region;
        this.phone = phone;
        this.imgLink = imgLink;
        this.uniqId=uniqId;
        this.isSelected=false;
    }
    public Product(){
        this.name = "";
        this.price = 0;
        this.category = "";
        this.region = "";
        this.phone = "";
        this.imgLink = "";
        this.uniqId="0";
        this.isSelected=false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }
}
