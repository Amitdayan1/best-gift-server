package com.dev.objects;

import java.util.List;

public class UserObject {
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private String password;
    private String token;
    private List<Product> productList;


    public UserObject(String firstName, String lastName, String username, String emailAddress, String password, String token,List<Product> productList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.token = token;
        this.productList=productList;
    }
    public UserObject() {
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.emailAddress = "";
        this.password = "";
        this.token = "";
        this.productList=null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
