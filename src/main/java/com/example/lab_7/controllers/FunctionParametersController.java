package com.example.lab_7.controllers;


import com.example.lab_7.App;
import com.example.lab_7.functions.AbstractTabulatedFunction;
import com.example.lab_7.ui.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FunctionParametersController implements Controller {

    private Stage _stage;
    public static int DEFAULT_LEFT_BORDER = 1;
    public static int DEFAULT_RIGHT_BORDER = 10;
    public static int DEFAULT_SIZE = 10;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField edLeftBorder;
    @FXML
    private TextField edRightBorder;
    @FXML
    private Button okButton;
    @FXML
    private Spinner<Integer> spPointsCount;

    /**
     * Инициализация
     */
    @FXML
    void initialize() {
        edLeftBorder.setText(String.valueOf(DEFAULT_LEFT_BORDER));
        edRightBorder.setText(String.valueOf(DEFAULT_RIGHT_BORDER));
        spPointsCount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(AbstractTabulatedFunction.MIN_SIZE,
                        AbstractTabulatedFunction.MAX_SIZE, DEFAULT_SIZE));
    }

    /**
     * Вернёт указанную левую границу
     *
     * @return левая граница
     */
    public Double getLeftDomainBorder() {
        return Double.valueOf(edLeftBorder.getText());
    }

    /**
     * Вернёт указанную правую границу
     *
     * @return правая граница
     */
    public Double getRightDomainBorder() {
        return Double.valueOf(edRightBorder.getText());
    }

    /**
     * Вернёт указанное кол-во точек
     *
     * @return кол-во точек
     */
    public Integer pointsCount() {
        return spPointsCount.getValue();
    }

    /**
     * Событие нажатия на кнопку Отмены
     */
    @FXML
    void cancelPressed() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    /**
     * Событие нажатия на кнопку OK
     */
    @FXML
    void okPressed() {
        try {
            App.tabFDoc.newFunction(getLeftDomainBorder(), getRightDomainBorder(), pointsCount());
            ((Stage) okButton.getScene().getWindow()).close();
        } catch (IllegalArgumentException e) {
            Alerts.showIllegalArgument();
        }
    }

    @Override
    public Stage getStage() {
        return _stage;
    }

    @Override
    public void setStage(Stage stage) {
        this._stage = stage;
        this._stage.setOnCloseRequest(event -> cancelPressed());
    }
}
