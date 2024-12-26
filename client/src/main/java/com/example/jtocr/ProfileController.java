package com.example.jtocr;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ProfileController extends AnchorPane implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private TextField password, username;
    @FXML
    private Hyperlink logout;
    @FXML
    private Button save;
    private File selectedFile;
    private Main application;
    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Image image = new Image("file:///C:\\Projects\\Java Tech\\JTOCR\\src\\main\\resources\\com\\example\\jtocr\\img\\profile-icon.png");
            profileImage.setImage(image);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        profileImage.setOnMouseClicked(event -> {
            selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                profileImage.setImage(image);

            }
        });
    }

    public void processLogout(ActionEvent event) {
        if (application == null) {
            return;
        }
        application.userLogout();
    }

    public void resetProfile(ActionEvent event) {
        if (application == null) {
            return;
        }
        username.setText("");
        password.setText("");
    }
}
