package com.example.jtocr.Controller;

import com.example.jtocr.Main;
import com.example.jtocr.model.CCTV;
import com.example.jtocr.model.Document;
import com.example.jtocr.routes.HttpRequestSender;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class footageController implements Initializable {

    @FXML
    private Button backButton, playButton, screenshotButton;
    @FXML
    private Slider slider;
    @FXML
    private MediaView mediaView;
    private Timeline timeline;
    private static Connection connection = null;
    private Main application;
    private String[] videoPaths = {
            "file:///C:/Users/User/Videos/videos/video1.mp4",
            "file:///C:/Users/User/Videos/videos/video2.mp4",
            "file:///C:/Users/User/Videos/videos/video3.mp4",
            "file:///C:/Users/User/Videos/videos/video5.mp4"
    };

    private int CCTVNb = CCTV.getCCTVNb();
    private Document document = new Document();

    public void setDocument(Document document) {
        this.document = document;
    }
    public void setApp(Main application) {
        this.application = application;
    }
    public void receiveInt(int index) {
        Media media = new Media(videoPaths[index]);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            slider.setValue(newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds() * 100.0);
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline();

        backButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.close();
                application.gotoCCTV();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        playButton.setOnAction(event -> {
            MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
            if (mediaPlayer != null) {
                MediaPlayer.Status status = mediaPlayer.getStatus();
                if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status ==
                        MediaPlayer.Status.STOPPED) {
                    mediaPlayer.play();
                    playButton.setText("Pause");
                } else {
                    mediaPlayer.pause();
                    playButton.setText("Play");
                }

                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    slider.setValue(0);
                    playButton.setText("Replay");
                });
            }
        });

        screenshotButton.setOnAction(event -> {

            WritableImage image = mediaView.snapshot(new SnapshotParameters(), null);
            String timestamp = String.valueOf(System.currentTimeMillis());
            String filename = "screenshot_" + timestamp + ".png";
            String screenshotPath = "screenshots/" + filename;
            File file = new File("screenshots/" + filename);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int newDocId = -1;
            HttpRequestSender sender = new HttpRequestSender();

            try {
                String URL = "http://localhost:8080/api/documents";
                String method = "POST";
                String contentType = "application/json";

                JSONObject requestBodyJson = new JSONObject();
                requestBodyJson.put("path", screenshotPath);
                requestBodyJson.put("name", filename);
                requestBodyJson.put("cctv_nb", CCTVNb);
                String requestBody = requestBodyJson.toString();

                String response = sender.sendHttpRequest(URL, method, contentType, requestBody);
                System.out.println("Response: " + response);

                newDocId = Integer.parseInt(response);

                document.setDocumentId(newDocId);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (newDocId != -1) {
                System.out.println(newDocId);

                try {
                    Stage stage = (Stage) screenshotButton.getScene().getWindow();
                    stage.close();
                    application.gotoMain(filename);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


        slider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging) {
                MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                Duration duration = mediaPlayer.getTotalDuration();
                Duration seekTo = duration.multiply(slider.getValue() / 100.0);
                mediaPlayer.seek(seekTo);

                timeline.stop();
                timeline.getKeyFrames().clear();
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    Duration totalTime = mediaPlayer.getTotalDuration();
                    slider.setValue(currentTime.toSeconds() / totalTime.toSeconds() * 100.0);
                    if (currentTime.equals(totalTime)) {
                        mediaPlayer.stop();
                        playButton.setText("Play");
                        timeline.stop();
                    }
                }));
                timeline.play();
            } else {
                timeline.pause();
            }
        });

        Timeline sliderUpdate = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
                    if (mediaPlayer != null) {
                        Duration currentTime = mediaPlayer.getCurrentTime();
                        Duration totalDuration = mediaPlayer.getTotalDuration();
                        double position = currentTime.toMillis() / totalDuration.toMillis() * 100.0;
                        slider.setValue(position);
                    }
                })
        );
        sliderUpdate.setCycleCount(Timeline.INDEFINITE);
        sliderUpdate.play();


    }
}