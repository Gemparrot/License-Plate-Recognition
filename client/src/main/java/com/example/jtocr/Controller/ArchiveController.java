package com.example.jtocr.Controller;

import com.example.jtocr.Main;
import com.example.jtocr.routes.HttpRequestSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchiveController implements Initializable {
    @FXML
    private Button CCTVButton, mainPageButton;
    @FXML
    private ImageView profileImage;
    @FXML
    private ListView<Pane> licensePlateListView;
    private Main application;
//    private long id = User.getId();


    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image image = new Image("file:///C:\\Projects\\Java Tech\\JTOCR\\src\\main\\resources\\com\\example\\jtocr\\img\\profile-icon.png");
            profileImage.setImage(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        CCTVButton.setOnAction(event -> {
            try {
                application.gotoCCTV();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mainPageButton.setOnAction(event -> {
            try {
                application.gotoMain();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        profileImage.setOnMouseClicked(event -> {
            try {
                application.gotoProfile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ObservableList<Pane> licensePlates = FXCollections.observableArrayList();

        HttpRequestSender sender = new HttpRequestSender();

        try {
            String URL = "http://localhost:8080/api/documents/{userId}";
            String method = "GET";
            String contentType = null;
            String requestBody = null;

            String response = sender.sendHttpRequest(URL, method, contentType, requestBody);
            System.out.println("Response: " + response);

            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String licensePlate = jsonObject.getString("license_plate");
                String docName = jsonObject.getString("name");
                String CCTVNb = jsonObject.getString("cctv_nb");

                if (licensePlate != null) {
                    Label licensePlateLabel = new Label("License Plate: " + licensePlate);
                    Label CCTVNbLabel = new Label("Camera: " + CCTVNb);
                    Button deleteButton = new Button("Delete");
                    Pane pane = new Pane();
                    HBox hbox = new HBox(10);
                    hbox.getChildren().addAll(licensePlateLabel, CCTVNbLabel, deleteButton);
                    pane.getChildren().add(hbox);
                    licensePlates.add(pane);

                    deleteButton.setOnAction(event -> {
                        try {
                            String deleteUrl = "http://localhost:8080/api/documents/{documentId}"; // Replace with your actual delete endpoint URL
                            String deleteMethod = "DELETE";
                            String deleteContentType = "application/json";

                            JSONObject deleteJson = new JSONObject();
                            deleteJson.put("license_plate", licensePlate);
                            deleteJson.put("name", docName);
                            deleteJson.put("cctv_nb", CCTVNb);
                            String deleteRequestBody = deleteJson.toString();

                            String deleteResponse = sender.sendHttpRequest(deleteUrl, deleteMethod, deleteContentType, deleteRequestBody);
                            System.out.println("Delete Response: " + deleteResponse);

                            licensePlates.remove(pane);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        licensePlateListView.setItems(licensePlates);
    }
}

