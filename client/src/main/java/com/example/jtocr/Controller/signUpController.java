package com.example.jtocr.Controller;

import com.example.jtocr.Main;
import com.example.jtocr.model.User;
import com.example.jtocr.routes.HttpRequestSender;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signUpController implements Initializable {
    @FXML
    Button signUp, login;
    @FXML
    TextField usernameTextField, passwordTextField;

    String user, password;
    private Main application;

    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signUp.setOnAction(actionEvent -> {
            User user = new User();
            user.setName(usernameTextField.getText());
            user.setPassword(passwordTextField.getText());

            HttpRequestSender sender = new HttpRequestSender();

            try {
                String URL = "http://localhost:8080/api/users/signup";

                String method = "POST";
                String contentType = "application/json";

                JSONObject json = new JSONObject();
                json.put("username", user);
                json.put("password", password);
                String requestBody = json.toString();

                String response = sender.sendHttpRequest(URL, method, contentType, requestBody);
                System.out.println("Response: " + response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        login.setOnAction(actionEvent -> {
            application.gotoLogin();
        });

    }
}


