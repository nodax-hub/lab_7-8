package com.example.lab_7;

import com.example.lab_7.controllers.Controller;
import com.example.lab_7.functions.FunctionPoint;
import com.example.lab_7.functions.exceptions.FunctionPointIndexOutOfBoundsException;
import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;
import com.example.lab_7.ui.Alerts;
import com.example.lab_7.ui.FunctionPointT;
import com.example.lab_7.ui.exceptions.FileIsNotAssignedException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Контроллер основного окна приложения
 */
public class mainController implements Controller {

    /**
     * Объект платформы (сцены) создания новой функции
     */
    private final Stage stageFunctionDialog = new Stage();
    /**
     * Объект платформы (сцены) операций над функцией
     */
    private final Stage stageOperationsDialog = new Stage();

    /**
     * Объект для работы с оконным выбором файлов
     */
    private final FileChooser fileChooserSave = new FileChooser();
    private final FileChooser fileChooserOpen = new FileChooser();

    // ======================= перечисление объектов сцены =========================
    public Button addPointButton;
    public Button deletePointButton;
    public Button setPointButton;
    public MenuItem newDocument;
    public MenuItem saveDocument;
    public MenuItem openAndSaveDocument;
    public MenuItem exit;
    public MenuItem loadDocument;
    public MenuItem functionOperations;
    /**
     * Индекс выбранной точки
     */
    private int _selectedPointIndex = 0;

    // ========================== логические переменные =============================
    /**
     * Флаг отвечающий за слежение: выбрана ли какая нибудь точка.
     */
    private boolean _selected = false;
    // ==================== перечисление объектов ===================
    @FXML
    private TextField edX;
    @FXML
    private TextField edY;
    @FXML
    private MenuItem openFile;
    @FXML
    private TableView<FunctionPointT> pointsTable;
    @FXML
    private TableColumn<FunctionPointT, Double> tableX;
    @FXML
    private TableColumn<FunctionPointT, Double> tableY;
    @FXML
    private Label selectedPoint;
    private ObservableList<FunctionPointT> pointsList;
    /**
     * основная платформа (сцена)
     */
    private Stage stage;

    {
        fileChooserSave.setInitialDirectory(new File("."));
        fileChooserOpen.setInitialDirectory(new File("."));
        fileChooserSave.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON document", "*.json"));
        fileChooserOpen.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON document", "*.json"));
    }

    /**
     * Событие после нажатия кнопки добавления точки
     */
    @FXML
    void addPoint() {
        try {
            App.tabFDoc.addPoint(new FunctionPoint(Double.parseDouble(edX.getText()), Double.parseDouble(edY.getText())));
        } catch (InappropriateFunctionPointException e) {
            Alerts.showInappropriatePoint();
        } catch (IllegalArgumentException e) {
            Alerts.showIllegalArgument();
        }
    }

    /**
     * Событие после нажатия кнопки удаления точки
     */
    @FXML
    void deletePoint() {
        if (!_selected) {
            Alerts.showUnselectedPoint();
            return;
        }

        try {
            App.tabFDoc.deletePoint(_selectedPointIndex);
        } catch (IllegalStateException e) {
            Alerts.showIllegalState();
        } catch (FunctionPointIndexOutOfBoundsException e) {
            Alerts.showIndexOutOfBounds();
        }
    }

    /**
     * Событие после нажатия на кнопку установить значение
     */
    @FXML
    void setPoint() {
        if (!_selected) {
            Alerts.showUnselectedPoint();
            return;
        }

        try {
            String x = edX.getText(), y = edY.getText();
            if (x.equals("")) {
                App.tabFDoc.setPointY(_selectedPointIndex, Double.parseDouble(y));
            } else {
                App.tabFDoc.setPoint(_selectedPointIndex,
                        new FunctionPoint(Double.parseDouble(x), Double.parseDouble(y)));
            }
        } catch (InappropriateFunctionPointException e) {
            Alerts.showInappropriatePoint();
        } catch (FunctionPointIndexOutOfBoundsException e) {
            Alerts.showIndexOutOfBounds();
        } catch (IllegalArgumentException e) {
            Alerts.showIllegalArgument();
        }
    }

    /**
     * Событие выбора клавиатурой точки в таблице
     */
    @FXML
    void selectingPointKey() {
        _selectedPointIndex = pointsTable.getSelectionModel().getFocusedIndex();
        _selected = true;
        updateTextSelectedPoint();
    }

    /**
     * Событие выбора мышью точки в таблице
     */
    @FXML
    void selectingPointMouse() {
        _selected = true;
        _selectedPointIndex = pointsTable.getSelectionModel().getFocusedIndex();
        updateTextSelectedPoint();
    }

    /**
     * Диалог создания новой функции
     */
    @FXML
    void showNewFunctionDialog() {
        stageFunctionDialog.showAndWait();
    }


    /**
     * Диалог открытия файла
     */
    @FXML
    void showOpenFileDialog() {
        try {
            File file = fileChooserOpen.showOpenDialog(openFile.getParentMenu().getParentPopup());
            App.tabFDoc.loadFile(file.getPath());
        } catch (RuntimeException ignored) {
        }
    }

    /**
     * Диалог загрузки функции из файла
     */
    @FXML
    void showLoadFunctionDialog() {
        Alerts.showComingSoon();
    }

    /**
     * Диалог операций с функциями
     */
    @FXML
    void showFunctionOperationDialog() {
        stageOperationsDialog.showAndWait();
    }

    /**
     * Диалог сохранения функции в файл
     */
    @FXML
    void showSaveAsDialog() {
        try {
            File file = fileChooserSave.showSaveDialog(openFile.getParentMenu().getParentPopup());
            App.tabFDoc.saveFunctionAs(file.getPath());
        } catch (RuntimeException ignored) {
        }
    }

    /**
     * Сохранение текущего файла
     */
    @FXML
    void saveDocumentInFile() {
        try {
            App.tabFDoc.saveFunction();
        } catch (FileIsNotAssignedException e) {
            showSaveAsDialog();
        }
    }

    /**
     * Выход из приложения через меню файла
     */
    @FXML
    void exitApplication() {
        if (App.tabFDoc.isModified())
            Alerts.showUnsavedChanges();
        else
            System.exit(0);
    }


    /**
     * Инициализация
     */
    @FXML
    private void initialize() {
        //для отображения значений в таблице
        tableX.setCellValueFactory(new PropertyValueFactory<>("x"));
        tableY.setCellValueFactory(new PropertyValueFactory<>("y"));


        pointsList = pointsTable.getItems();
        //Устанавливаем связь с объектом документа приложения (регистрируем текущий контроллер)
        App.tabFDoc.registerRedrawFunctionController(this);

        //Загружаем окно для установки параметров новой функции
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNewDocForm.fxml"));
            stageFunctionDialog.setTitle("Параметры функции");
            stageFunctionDialog.setResizable(false);
            stageFunctionDialog.setScene(new Scene(loader.load()));
            stageFunctionDialog.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        //Загружаем окно для установки параметров новой функции
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLOperationsForm.fxml"));
            stageOperationsDialog.setTitle("Операции");
            stageOperationsDialog.setResizable(false);
            stageOperationsDialog.setScene(new Scene(loader.load()));
            stageOperationsDialog.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Выполняет перерисовку таблицы
     */
    public void redraw() {
        pointsList.clear();

        for (FunctionPoint point : App.tabFDoc) {
            FunctionPointT pointT = new FunctionPointT(point);
            pointsList.add(pointT);
        }

        _selected = false;
        updateTextSelectedPoint();
    }

    /**
     * Обновляет текст выбранной точки
     */
    private void updateTextSelectedPoint() {
        selectedPoint.setText("Выбранная точка: %s из %d.".formatted(_selected ? _selectedPointIndex + 1 : "?",
                App.tabFDoc.getPointsCount()));
    }

    /**
     * Получить основную сцену
     *
     * @return главная сцена
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Устанавливает платформу
     *
     * @param stage первоначальная платформа
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

