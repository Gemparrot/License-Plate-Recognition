package com.example.jtocr.security;

import com.example.jtocr.routes.HttpRequestSender;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Authenticator {
    private static final Map<String, String> USERS = new HashMap<String, String>();
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String dbPath = "jdbc:mysql://localhost:3307/";
    private static final String dbName = "ocr";
    private static final String user = "root";
    private static final String pwd = "";
    private static Connection connection = null;

    public static boolean validate(String user, String password) {
        HttpRequestSender sender = new HttpRequestSender();

        try {
            String url = "http://localhost:8080/api/users/login";
            String method = "POST";
            String contentType = "application/json";

            JSONObject json = new JSONObject();
            json.put("username", user);
            json.put("password", password);
            String requestBody = json.toString();

            String response = sender.sendHttpRequest(url, method, contentType, requestBody);
            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
