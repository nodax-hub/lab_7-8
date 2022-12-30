module com.example.lab_ {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires java.xml.crypto;

    opens com.example.lab_7 to javafx.fxml;
    exports com.example.lab_7;
    exports com.example.lab_7.ui;
    opens com.example.lab_7.ui to javafx.fxml;
    exports com.example.lab_7.controllers;
    opens com.example.lab_7.controllers to javafx.fxml;
}