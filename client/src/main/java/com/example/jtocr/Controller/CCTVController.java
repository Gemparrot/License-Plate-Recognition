package com.example.jtocr.Controller;

import com.example.jtocr.model.CCTV;
import javafx.embed.swing.SwingFXUtils;
import com.example.jtocr.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CCTVController implements Initializable {
    @FXML
    private MediaView mediaView1;
    @FXML
    private MediaView mediaView2;
    @FXML
    private MediaView mediaView3;
    @FXML
    private MediaView mediaView4;
    private MediaPlayer[] mediaPlayers = new MediaPlayer[4];
    private MediaView[] mediaViews = new MediaView[4];
    private String[] screenshotPaths = new String[4];
    private static Connection connection = null;
    @FXML
    private Button archiveButton, mainPageButton;
    @FXML
    private ImageView profileImage;
    private Main application;
    private String[] videoPaths = {
            "file:///C:/Users/User/Videos/videos/video1.mp4",
            "file:///C:/Users/User/Videos/videos/video2.mp4",
            "file:///C:/Users/User/Videos/videos/video3.mp4",
            "file:///C:/Users/User/Videos/videos/video5.mp4"
    };

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
        archiveButton.setOnAction(event -> {
            try {
                application.gotoArchive();
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


        mediaViews = new MediaView[]{mediaView1, mediaView2, mediaView3, mediaView4};
        for (int i = 0; i < mediaViews.length; i++) {
            mediaViews[i].setFitWidth(400);
            mediaViews[i].setFitHeight(300);

            Media media1 = new Media(videoPaths[0]);
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaView1.setMediaPlayer(mediaPlayer1);

            Media media2 = new Media(videoPaths[1]);
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
            mediaView2.setMediaPlayer(mediaPlayer2);

            Media media3 = new Media(videoPaths[2]);
            MediaPlayer mediaPlayer3 = new MediaPlayer(media3);
            mediaView3.setMediaPlayer(mediaPlayer3);

            Media media4 = new Media(videoPaths[3]);
            MediaPlayer mediaPlayer4 = new MediaPlayer(media4);
            mediaView4.setMediaPlayer(mediaPlayer4);
        }
    }

    private void playVideo(int index) {
        mediaPlayers[index].play();
    }

    private void takeScreenshot(int index) {
        mediaPlayers[index].pause();

        MediaView mediaView = mediaViews[index];
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        WritableImage screenshot = mediaView.snapshot(params, null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Screenshot");
        fileChooser.setInitialFileName("screenshot.png");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", file);
                screenshotPaths[index] = file.getAbsolutePath();
                System.out.println("Screenshot saved to: " + screenshotPaths[index]);
            } catch (IOException e) {
                System.out.println("Error saving screenshot: " + e.getMessage());
            }
        }

        mediaPlayers[index].play();
    }

    public void handleMediaViewClick(MouseEvent mouseEvent) throws IOException {

        MediaView clickedMediaView = (MediaView) mouseEvent.getSource();

        int index = -1;
        if (clickedMediaView.equals(mediaView1)) {
            index = 0;
        } else if (clickedMediaView.equals(mediaView2)) {
            index = 1;
        } else if (clickedMediaView.equals(mediaView3)) {
            index = 2;
        } else if (clickedMediaView.equals(mediaView4)) {
            index = 3;
        }
        CCTV.getCCTVNb(index);
        System.out.println(index);
        application.gotoVideo(index);
    }
}
