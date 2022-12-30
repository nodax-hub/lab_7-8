package com.example.lab_7.functions;

import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;

/**
 * Класс реализует табулированную функцию в виде двусвязного цикличного списка.
 *
 * @author NODAX
 * @see com.example.lab_7.functions.FunctionPoint
 * @see CloseLinkedList
 */
@SuppressWarnings("unused")
public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {

    // ====================================  Поля  ========================================
    /**
     * Список точек
     */
    private CloseLinkedList<FunctionPoint> _points;


    // ================================  Конструкторы  ===================================

    /**
     * Конструктор табулированной функции по левой, правой и значениям точек по y
     */
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] yValues) throws IllegalArgumentException {
        //Создаём родительский класс
        super(leftX, rightX);
        //Производим необходимые проверки
        isValidSize(yValues.length);

        //Устанавливаем значения полей
        _points = new CloseLinkedList<>();

        //Получаем массив, равномерно распределённых на заданном участке, значений по оси абсцисс
        double[] xValues = xRange(leftX, rightX, yValues.length);

        //Наконец заполняем массив точек полученными координатами точек
        for (int i = 0; i < yValues.length; i++)
            _points.pushBack(new FunctionPoint(xValues[i], yValues[i]));
    }


    /**
     * Конструктор табулированной функции по левой, правой и кол-ву точек
     */
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        this(leftX, rightX, new double[pointsCount]);
    }

    /**
     * Конструктор табулированной функции по готовому набору точек
     *
     * @param points массив точек типа FunctionPoint[]
     * @see FunctionPoint
     */
    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        super(points[0].getX(), points[points.length - 1].getX());
        //Производим необходимые проверки
        isValidSize(points.length);

        //Устанавливаем значения полей
        _points = new CloseLinkedList<>();

        //Копирование в случае успешной проверки условий
        _points = new CloseLinkedList<>();
        for (FunctionPoint point : points) {
            _points.pushBack(point.clone());
        }
    }


    // ===========================  Публичные методы доступа  ============================
    @Override
    public int getPointsCount() {
        return _points.size();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(_points.getData(index));
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        //Проверка валидности индекса
        isValidIndex(index);
        //Проверка корректности в соответствии с соседними значениями абсцисс
        outsideOfDefinition(index, point);
        //Замена текущей точки на новую
        _points.setData(index, point.clone());
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        //Ищем место для вставки нового элемента
        int place = getPlaceForPoint(point);
        //Вставляем элемент
        _points.insert(point.clone(), place);
    }

    @Override
    public void deletePoint(int index) {
        isValidIndex(index);
        isMinSize();
        _points.popAt(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinkedListTabulatedFunction list)
            return _points.equals(list._points);
        else
            return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return _points.hashCode();
    }

    @Override
    public LinkedListTabulatedFunction clone() {
        LinkedListTabulatedFunction clone = (LinkedListTabulatedFunction) super.clone();
        clone._points = new CloseLinkedList<>();
        for (int i = 0; i < getPointsCount(); ++i) {
            clone._points.pushBack(_points.getData(i).clone());
        }
        return clone;
    }

    public FunctionPoint[] toArray() {
        return (FunctionPoint[]) _points.toArray();
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

        @Override
        public LinkedListTabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public LinkedListTabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public LinkedListTabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }
    }
}
