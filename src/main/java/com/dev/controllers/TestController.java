package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.Product;
import com.dev.objects.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {
    private  List<UserObject> userObjects;
    private List<Product> products;
    private List<Product> cartList;
    private String accessKey;

    @PostConstruct
    private void init () {
        userObjects = new ArrayList<>();
        products=new ArrayList<>();
        cartList=new ArrayList<>();

        accessKey="?access_key=31ac57b1c066ae6c762417e3a95af060&symbols=";
    }
    @Autowired
    private Persist persist;

    @RequestMapping("sign-in")
    public String signIn (String username, String password) {
        return persist.doesUserExists(username, password);
    }
    @RequestMapping("test")
    public String test(String username){
        return persist.doesUsernameFree(username);}

//    @RequestMapping("sign-up")
//    public boolean createAccount (String firstName,String lastName,String username,String emailAddress, String password) {
//        boolean alreadyExists = false;
//      alreadyExists=persist.doesUsernameFree(username);
//        if (alreadyExists) {
//            UserObject userObject = new UserObject();
//            userObject.setFirstName(firstName);
//            userObject.setLastName(lastName);
//            userObject.setUsername(username);
//            userObject.setEmailAddress(emailAddress);
//            userObject.setPassword(password);
//            String hash = createHash(username, password);
//            userObject.setToken(hash);
//            this.userObjects.add(userObject);
//
//        }
//        return alreadyExists;
//    }

    public String createHash (String username, String password) {
        String myHash = null;
        try {
            String hash = "35454B055CC325EA1AF2126E27707052";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((username + password).getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myHash;
    }


    private UserObject getUserByToken (String token) {
        UserObject found = null;
        for (UserObject userObject : this.userObjects) {
            if (userObject.getToken().equals(token)) {
                found = userObject;
                break;
            }
        }
        return found;
    }
    private Product getProductByUniqId(String uniqId){
        Product found=null;
        for (Product product:this.products){
            if(product.getUniqId().equals(uniqId)){
                found=product;
                break;
            }
        }return found;
    }
    @RequestMapping("get-user")
    public UserObject getUser(String token){
        return getUserByToken(token);}
    @RequestMapping("get-products")
    public List<Product> getProducts(){
        return this.products;
    }
    @RequestMapping("set-user-cart")
    public void setUserCart(String token,String uniqId){
        Product product=getProductByUniqId(uniqId);
        product.setSelected(true);
        this.cartList.add(product);
    }
    @RequestMapping("update-user-cart")
    public void updateUserCart(String token,String uniqId){
        Product product=getProductByUniqId(uniqId);
        product.setSelected(false);
        this.cartList.remove(product);
    }
    @RequestMapping("get-user-cart")
    public List<Product> getUserCart(String token){
        return this.cartList;
    }

    @RequestMapping("access-key")
    public String getAccessKey () {
        return this.accessKey;
    }
//    @RequestMapping("test")
//    public List<Product> getTest() throws SQLException {
//        Persist persist=new Persist();
//        return persist.getProducts();
//    }

}

