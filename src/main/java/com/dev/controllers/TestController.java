package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.Product;
import com.dev.objects.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

        accessKey="?access_key=31ac57b1c066ae6c762417e3a95af060&symbols=";
    }
    @Autowired
    private Persist persist;

//    @RequestMapping("test")
//  public void test(){
//        persist.test("0546779544");
//    }
    @RequestMapping("user-log-in")
    public int userLogIn(String token){return persist.doesUserLoggedIn(token);}
    @RequestMapping("user-log-out")
    public void userLogOut(String token){persist.userLogOut(token);}
    @RequestMapping("sign-in")
    public String signIn (String username, String password) {
        return persist.userSignIn(username, password);
    }
    @RequestMapping("sign-up")
    public boolean createAccount (String firstName,String lastName,String username,String emailAddress, String password) {
        boolean isFree;
      isFree=persist.doesUsernameFree(username);
        if (isFree) {
            String hash = createHash(username, password);
            UserObject userObject = new UserObject(firstName,lastName,username,emailAddress,password,hash);
            persist.addUser(userObject);
        }
        return isFree;
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
    private UserObject getUserByToken (String token) {
        return persist.getUserByToken(token);
    }
    @RequestMapping("get-products")
    public List<Product> getProducts(){return persist.getProducts();}
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
