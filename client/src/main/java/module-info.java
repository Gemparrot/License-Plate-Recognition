module com.example.jtocr {
    requires javafx.controls;
    requires javafx.fxml;
    requires tess4j;
    requires java.logging;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.gson;
    requires org.json;

    opens com.example.jtocr to javafx.fxml;
    exports com.example.jtocr;

    opens com.example.jtocr.Controller to javafx.fxml;
    exports com.example.jtocr.Controller;

    exports com.example.jtocr.connection;
}