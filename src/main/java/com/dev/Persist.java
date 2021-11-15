package com.dev;

import com.dev.objects.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        }
    }

    public String doesUserExists(String username, String password) {
        //Should return boolean
        String token = "null";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "SELECT token FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String doesUsernameFree(String username) {
        //boolean flag = true;
        String tempUsername =null;
        try{
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT username FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            tempUsername = resultSet.getString("username");

        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempUsername;
    }
}