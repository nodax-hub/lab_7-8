package com.example.lab_7;

import com.example.lab_7.controllers.FunctionParametersController;
import com.example.lab_7.ui.TabulatedFunctionDoc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static final TabulatedFunctionDoc tabFDoc = new TabulatedFunctionDoc(
            FunctionParametersController.DEFAULT_LEFT_BORDER,
            FunctionParametersController.DEFAULT_RIGHT_BORDER,
            FunctionParametersController.DEFAULT_SIZE);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("FXMLMainForm.fxml"));
        Parent root = loader.load();
        mainController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Табулированные функции");
        stage.setResizable(false);
        stage.show();
    }
}
