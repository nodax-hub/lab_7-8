package com.example.lab_7.controllers;

import com.example.lab_7.App;
import com.example.lab_7.functions.Functions;
import com.example.lab_7.ui.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class OperationOnFunctionController implements Controller {
    private final DecimalFormat decimalFormat = new DecimalFormat("#.###");
    @FXML
    private Button cancelButton;
    @FXML
    private TextField edLeftBorderIntegrate;
    @FXML
    private TextField edRightBorderIntegrate;
    @FXML
    private TextField edTabulate;
    @FXML
    private Label integrateValue;
    @FXML
    private Button integrateButton;
    @FXML
    private Button tabulateButton;
    @FXML
    private Label tabulateValue;
    private Stage _primaryStage;

    @FXML
    void cancelPressed(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    void integratePressed(ActionEvent event) {
        try {
            double integral = Functions.integrate(App.tabFDoc,
                    Double.parseDouble(edLeftBorderIntegrate.getText()),
                    Double.parseDouble(edRightBorderIntegrate.getText())
            );
            integrateValue.setText(String.valueOf(decimalFormat.format(integral)));
        } catch (IllegalArgumentException e) {
            Alerts.showIllegalArgument();
        }
    }

    @FXML
    void tabulatePressed(ActionEvent event) {
        try {
            double tabulate = App.tabFDoc.getFunctionValue(Double.parseDouble(edTabulate.getText()));
            tabulateValue.setText(String.valueOf(decimalFormat.format(tabulate)));
        } catch (IllegalArgumentException e) {
            Alerts.showIllegalArgument();
        }
    }

    @Override
    public void setStage(Stage stage) {
        _primaryStage = stage;
    }

    @Override
    public Stage getStage() {
        return _primaryStage;
    }
}
