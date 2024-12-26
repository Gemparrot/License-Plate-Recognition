package com.example.jtocr.Controller;

import com.example.jtocr.Main;
import com.example.jtocr.model.User;
import com.example.jtocr.routes.HttpRequestSender;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.json.JSONObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mainPageController extends AnchorPane implements Initializable {
    @FXML
    private Label numberlabel;
    @FXML
    private Button archiveButton, CCTVButton, saveButton;
    @FXML
    private ImageView profileImage, documentImg;
    private String docName, username;
    private long id = User.getId();
    private String imageURL;
    private File selectedFile;
    private Main application;

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setApp(Main application) {
        this.application = application;
        User loggedUser = application.getLoggedUser();
        id = loggedUser.getId();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Image image = new Image("file:///C:\\Projects\\Java Tech\\JTOCR\\src\\main\\resources\\com\\example\\jtocr\\img\\profile-icon.png");
            profileImage.setImage(image);

            Image licenseImage = new Image("file:///C:\\Projects\\Java Tech\\JTOCR\\screenshots\\default_img.PNG");
            documentImg.setImage(licenseImage);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        archiveButton.setOnAction(event -> {
            try {
                application.gotoArchive();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        CCTVButton.setOnAction(event -> {
            try {
                application.gotoCCTV();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        saveButton.setOnAction(event -> {
            String number = numberlabel.getText();

            HttpRequestSender sender = new HttpRequestSender();
            try {
                String url = "http://localhost:8080/api/documents/{documentId}/licenseplate";

                String method = "PUT";
                String contentType = "application/json";

                JSONObject json = new JSONObject();
                json.put("licenseplate", number);
                String requestBody = json.toString();

                String response = sender.sendHttpRequest(url, method, contentType, requestBody);
                System.out.println("Response: " + response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        profileImage.setOnMouseClicked(event -> {
            try {
                application.gotoProfile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setImage() {
        try {
            imageURL = "file:///C:\\Projects\\Java Tech\\JTOCR\\screenshots\\" + docName;
            System.out.println(imageURL);
            Image licenseImage = new Image(imageURL);
            documentImg.setImage(licenseImage);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void Recognize(ActionEvent actionEvent) {
        System.out.println(imageURL);
        selectedFile = new File("screenshots\\" + docName);
        if (selectedFile == null || !selectedFile.exists()) {
            System.err.println("Error: selected file is null or does not exist");
            return;
        }
        Image image = new Image(selectedFile.toURI().toString());
        if (image.isError()) {
            System.err.println("Error loading image");
            image.getException().printStackTrace();
            return;
        }
        if (documentImg != null) {
            documentImg.setImage(image);
        } else {
            System.err.println("Error: imageView is null");
        }

        ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Program Files\\Java\\tess4j-tess4j-4.6.0\\tessdata");
        instance.setLanguage("eng");
        instance.setTessVariable("user_defined_dpi", "72");

        try {
            System.out.println("selectedFile: " + selectedFile);
            System.out.println("image: " + image);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            if (bufferedImage == null) {
                System.err.println("Error converting image to buffered image");
                return;
            }
            System.out.println("bufferedImage: " + bufferedImage);
            String result = instance.doOCR(bufferedImage);
            String licensePlate = extractLicensePlate(result);
            numberlabel.setText(licensePlate); // Set the result to the numberlabel label
        } catch (TesseractException ex) {
            System.err.println("Error occurred during recognition: " + ex.getMessage());
        }
    }

    public static String extractLicensePlate(String result) {
        String licensePlate = "";
        Pattern pattern = Pattern.compile("[A-Z][ ][0-9]{6}|[A-Z][0-9]{6}|[0-9]{7}");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            String matchedPlate = matcher.group().replaceAll("\\s+", "");
            String firstSixDigits = matchedPlate.substring(0, 6); // Take the first 6 characters of the plate number
            if (matchedPlate.length() > 6) {
                char firstChar = firstSixDigits.charAt(0);
                if (firstChar == '6') {
                    firstSixDigits = "G" + firstSixDigits.substring(1); // Change the first character to G
                } else if (firstChar == '0') {
                    firstSixDigits = "O" + firstSixDigits.substring(1); // Change the first character to O
                }
            }
            licensePlate = firstSixDigits + matchedPlate.substring(6); // Concatenate the modified license plate with the rest of the plate number
        }
        return licensePlate;
    }
}