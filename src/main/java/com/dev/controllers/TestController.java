package com.dev.controllers;

import com.dev.objects.Product;
import com.dev.objects.UserObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        Product p1=new Product("My Way",200,"Food","south","0546778559","images/myWay.PNG", "w1");
        Product p2=new Product("Orient Jerusalem",6500,"Vacation","Center","025955594","images/orient.PNG","w2");
        Product p3=new Product("Hotel Rimonim",3400, "Vacation", "South", "086988877","images/rimonim.PNG","w3");
        Product p4=new Product("Queen Of Sheba", 6500,"Vacation", "South", "086985337", "images/sheba.PNG","w4");
        Product p5=new Product( "Milos Dead Sea", 1000, "Vacation", "East", "029714557", "images/milos.PNG","w5");
        Product p6=new Product("H&M",30,"Clothing","South","1800993667","images/hNm.PNG","w6");
        Product p7=new Product("2C",600,"Food","Central","036595114","images/2c.PNG","w7");
        Product p8=new Product("Club Hotel",3500,"Vacation","North","086932447","images/clubHotel.PNG","w8");
        Product p9=new Product("Space",550,"Food","South","086935567","images/space.png","w9");
        this.products.add(p1);
        this.products.add(p2);
        this.products.add(p3);
        this.products.add(p4);
        this.products.add(p5);
        this.products.add(p6);
        this.products.add(p7);
        this.products.add(p8);
        this.products.add(p9);


        accessKey="?access_key=31ac57b1c066ae6c762417e3a95af060&symbols=";
    }
    @RequestMapping("sign-in")
    public String signIn (String username, String password) {
        String token = null;
        for (UserObject userObject : this.userObjects) {
            if (userObject.getUsername().equals(username) &&
                    userObject.getPassword().equals(password)) {
                token = userObject.getToken();
            }
        }
        return token;
    }

    @RequestMapping("sign-up")
    public boolean createAccount (String firstName,String lastName,String username,String emailAddress, String password) {
        boolean success = false;
        boolean alreadyExists = false;
        for (UserObject userObject : this.userObjects) {
            if (userObject.getUsername().equals(username)) {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            UserObject userObject = new UserObject();
            userObject.setFirstName(firstName);
            userObject.setLastName(lastName);
            userObject.setUsername(username);
            userObject.setEmailAddress(emailAddress);
            userObject.setPassword(password);
            String hash = createHash(username, password);
            userObject.setToken(hash);
            this.userObjects.add(userObject);
            success = true;
        }
        return success;
    }

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

}
