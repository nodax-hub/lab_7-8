package com.example.lab_7.ui;

import com.example.lab_7.controllers.Controller;
import com.example.lab_7.functions.Function;
import com.example.lab_7.functions.FunctionPoint;
import com.example.lab_7.functions.TabulatedFunction;
import com.example.lab_7.functions.TabulatedFunctions;
import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;
import com.example.lab_7.ui.exceptions.FileIsNotAssignedException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/**
 * Объект этого класса описывает открытый в программе документ, т.е.
 * содержит ссылку на объект табулированной функции в приватной переменной, текущее имя файла документа,
 * а также булевский флаг, показывающий то, изменялся ли документ с момента последнего сохранения.
 *
 * @author NODAX
 * @see TabulatedFunction
 */
@SuppressWarnings("unused")
public class TabulatedFunctionDoc implements TabulatedFunction {
    // ====================================  Поля  ========================================
    /**
     * Поле содержащее ссылку на объект табулированной функции, с документом которой мы работаем.
     */
    private TabulatedFunction _currentFunction;
    /**
     * Имя файла (документа табулированной функции)
     */
    private String _filename;
    /**
     * Логическое поле - показывает есть ли имя у данного документа
     */
    private boolean _fileNameAssigned = false;
    /**
     * Логическое поле - отвечает за проверку изменений.
     */
    private boolean _modified = false;
    /**
     * Контроллер
     */
    private Controller _controller = null;


    // =================================  Конструкторы  ===================================

    /**
     * Запрещаем создавать документ табулированной функции без параметров
     */
    private TabulatedFunctionDoc() {
    }

    /**
     * Конструктор документа табулированной функции по левой, правой границе и кол-ву точек.
     *
     * @param leftX       левая граница
     * @param rightX      правая граница
     * @param pointsCount кол-во точек
     */
    public TabulatedFunctionDoc(double leftX, double rightX, int pointsCount) {
        _currentFunction = TabulatedFunctions.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    /**
     * Конструктор док-та табулированной функции основанной на ф-и в указанном диапазоне с указанным кол-вом точек
     *
     * @param function    функция, которую будем табулировать
     * @param leftX       левая граница функции
     * @param rightX      правая граница функции
     * @param pointsCount кол-во точек
     */
    public TabulatedFunctionDoc(Function function, double leftX, double rightX, int pointsCount) {
        _currentFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
    }


    // ============================  Публичные методы доступа  ============================

    /**
     * Заменяет для данного документа табулированную функцию на новую с указанными параметрами
     *
     * @param leftX       левая граница
     * @param rightX      правая граница
     * @param pointsCount количество точек
     */
    public void newFunction(double leftX, double rightX, int pointsCount) {
        _currentFunction = TabulatedFunctions.createTabulatedFunction(leftX, rightX, pointsCount);
        _modified = true;
        callRedraw();
        callUpdateTitle();
    }

    /**
     * Заменяет табулированную функцию на новую,
     * полученную путём табулирования указанной функции с указанными параметрами.
     *
     * @param function    функция
     * @param leftX       левая граница
     * @param rightX      правая граница
     * @param pointsCount количество точек
     */
    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        _currentFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        _modified = true;
        callRedraw();
        callUpdateTitle();
    }

    /**
     * Сохраняет текущую табулированную функцию с указанным именем файла в формате JSON.
     *
     * @param filename имя файла для записи
     */
    @SuppressWarnings("unchecked")
    public void saveFunctionAs(String filename) {

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < _currentFunction.getPointsCount(); ++i) {
            FunctionPoint point = _currentFunction.getPoint(i);
            jsonObject.put(point.getX(), point.getY());
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Обновляем поля соответствующим образом
        _filename = filename;
        _fileNameAssigned = true;
        _modified = false;

        callUpdateTitle();
    }

    /**
     * Сохраняет текущий документ.
     * <p>
     * Сохраняет текущую табулированную функцию с текущим именем файла.
     * <p>
     * Выполняет перезапись файла.
     */
    public void saveFunction() {
        if (isFileNameAssigned())
            saveFunctionAs(_filename);
        else
            throw new FileIsNotAssignedException("file name not set");
    }

    /**
     * Загружает из файла с указанным именем табулированную функцию в формате JSON.
     *
     * @param filename имя файла для чтения
     */
    @SuppressWarnings("unchecked")
    public void loadFile(String filename) {
        try (FileReader file = new FileReader(filename)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(file);
            FunctionPoint[] points = new FunctionPoint[jsonObject.size()];
            int index = 0;
            for (Object obj : jsonObject.entrySet()) {
                Map.Entry<String, Double> point = (Map.Entry<String, Double>) obj;
                points[index] = new FunctionPoint(Double.parseDouble(point.getKey()), point.getValue());
                index++;
            }
            Arrays.sort(points, Comparator.comparingDouble(FunctionPoint::getX));
            _currentFunction = TabulatedFunctions.createTabulatedFunction(points);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        _filename = filename;
        _fileNameAssigned = true;
        _modified = false;

        callRedraw();
        callUpdateTitle();
    }


    /**
     * Устанавливает имя файла
     *
     * @param filename новое имя файла
     */
    public void setFilePath(String filename) {
        _filename = filename;
        _fileNameAssigned = true;
        callUpdateTitle();
    }

    /**
     * Отражает - был ли изменён документ.
     *
     * @return {@code true} когда был изменён, иначе {@code false}
     */
    public boolean isModified() {
        return _modified;
    }

    /**
     * Отражает - подписан ли данный файл (есть ли у него имя).
     *
     * @return {@code true} когда имя файла есть, иначе {@code false}
     */
    public boolean isFileNameAssigned() {
        return _fileNameAssigned;
    }


    /**
     * Привязывает к объекту основной контроллер
     *
     * @param controller контроллер
     */
    public void registerRedrawFunctionController(Controller controller) {
        _controller = controller;
        callRedraw();
    }

    /**
     * Вызывает метод перерисовки если задан контроллер
     */
    public void callRedraw() {
        if (_controller != null)
            _controller.redraw();
    }

    /**
     * Вызывает метод обновления заголовка файла
     */
    public void callUpdateTitle() {
        if (_controller != null)
            _controller.updateTitle(getCurrentTitle());
    }


    // =============  Реализация методов интерфейса табулированной функции  ===============

    @Override
    public int getPointsCount() {
        return _currentFunction.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return _currentFunction.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        _modified = true;
        _currentFunction.setPoint(index, point);
        callRedraw();
        callUpdateTitle();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        _currentFunction.addPoint(point);
        _modified = true;
        callRedraw();
        callUpdateTitle();
    }

    @Override
    public void deletePoint(int index) {
        _currentFunction.deletePoint(index);
        _modified = true;
        callRedraw();
        callUpdateTitle();
    }

    @Override
    public double getFunctionValue(double x) {
        return _currentFunction.getFunctionValue(x);
    }

    @Override
    public String toString() {
        return "TabulatedFunctionDoc:" + '\n' +
                "filename: " + _filename + '\n' +
                "TabulatedFunction: " + _currentFunction.toString();
    }

    @Override
    public TabulatedFunction clone() {
        try {
            TabulatedFunctionDoc functionDoc = (TabulatedFunctionDoc) super.clone();
            functionDoc._currentFunction = _currentFunction.clone();
            return functionDoc;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return _currentFunction.iterator();
    }


    // ====================  Переопределение методов класса Object  =======================

    @Override
    public int hashCode() {
        return _currentFunction.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TabulatedFunctionDoc tfDoc
                && _currentFunction.equals(tfDoc._currentFunction) && _filename.equals(tfDoc._filename);
    }

    /**
     * Вернёт имя текущего заголовка
     *
     * @return имя в соответствии с текущими параметрами документа
     */
    protected String getCurrentTitle() {
        String result = isFileNameAssigned() ? _filename : "Безымянный";
        return (isModified() ? "*" : "") + result + " - Табулированные функции";
    }
}