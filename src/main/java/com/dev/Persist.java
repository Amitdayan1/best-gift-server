package com.dev;

import com.dev.objects.Product;
import com.dev.objects.UserObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.Console;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {
    private Connection connection;

    @PostConstruct
    public void createConnectionToDatabase() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/best_gift?useSSL=false", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public String userSignIn(String username, String password) {
        String token = "";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT token FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString("token");
                PreparedStatement preparedStatement1 = this.connection.prepareStatement
                        ("UPDATE users SET isLoggedOn=1 WHERE username=?");
                preparedStatement1.setString(1, username);
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }
    public void userLogOut(String token) {
        try {
            PreparedStatement preparedStatement1 = this.connection.prepareStatement
                    ("UPDATE users SET isLoggedOn=0 WHERE token=?");
            preparedStatement1.setString(1, token);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean doesUsernameFree(String username) {
        boolean flag = true;
        String tempUsername;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT username FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tempUsername = resultSet.getString("username");
                if (tempUsername.equals(username)) {
                    flag = false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT * FROM Products");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getInt("price"));
                product.setCategory(rs.getString("category"));
                product.setRegion(rs.getString("region"));
                product.setPhone(rs.getString("phone"));
                product.setImgLink(rs.getString("imgLink"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public UserObject getUserByToken(String token) {
        UserObject userObject = new UserObject();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT * FROM users WHERE token=?");
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userObject.setFirstName(rs.getString("firstName"));
                userObject.setLastName(rs.getString("lastName"));
                userObject.setUsername(rs.getString("username"));
                userObject.setEmailAddress(rs.getString("emailAddress"));
                userObject.setPassword(rs.getString("password"));
                userObject.setToken(token);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userObject;
    }

    public void addUser(UserObject userObject) {
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(" INSERT INTO users (firstName,lastName,username,emailAddress,password,token,isLoggedOn)"
                    + " values (?, ?, ?, ?, ?,?,0)");
            preparedStmt.setString(1, userObject.getFirstName());
            preparedStmt.setString(2, userObject.getLastName());
            preparedStmt.setString(3, userObject.getUsername());
            preparedStmt.setString(4, userObject.getEmailAddress());
            preparedStmt.setString(5, userObject.getPassword());
            preparedStmt.setString(6, userObject.getToken());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int doesUserLoggedIn(String token) {
        int flag=0;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT isLoggedOn FROM users WHERE token = ?");
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                flag = rs.getInt("isLoggedOn");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
