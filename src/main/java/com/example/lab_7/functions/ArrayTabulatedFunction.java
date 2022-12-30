package com.example.lab_7.functions;

import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;

import java.util.Arrays;

import static java.lang.Math.max;

/**
 * Класс - реализует табулированную функцию в виде стандартного java массива.
 *
 * @author NODAX
 * @see com.example.lab_7.functions.FunctionPoint
 */
@SuppressWarnings("unused")
public class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    // ====================================  Поля  ========================================
    /**
     * Коэффициент запаса
     */
    private final static double REALLOCATE_MULTIPLIER = 2.0;
    /**
     * Массив точек
     */
    private FunctionPoint[] _points;
    /**
     * Текущее кол-во точек
     */
    private int _pointsCount;


    // ================================  Конструкторы  ===================================

    /**
     * Конструктор табулированной функции по левой, правой и значениям точек по y
     */
    public ArrayTabulatedFunction(double leftX, double rightX, double[] yValues) throws IllegalArgumentException {
        //Создаём родительский класс
        super(leftX, rightX);
        //Производим необходимые проверки
        isValidSize(yValues.length);

        //Устанавливаем значения полей
        _pointsCount = yValues.length;
        _points = new FunctionPoint[_pointsCount];

        //Получаем массив, равномерно распределённых на заданном участке, значений по оси абсцисс
        double[] xValues = xRange(leftX, rightX, _pointsCount);

        //Наконец заполняем массив точек полученными координатами точек
        for (int i = 0; i < _pointsCount; i++)
            _points[i] = new FunctionPoint(xValues[i], yValues[i]);

    }


    /**
     * Конструктор табулированной функции по левой, правой и кол-ву точек
     */
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        this(leftX, rightX, new double[pointsCount]);
    }

    /**
     * Конструктор табулированной функции по готовому набору точек
     *
     * @param points массив точек типа FunctionPoint[]
     * @see FunctionPoint
     */
    public ArrayTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        super(points[0].getX(), points[points.length - 1].getX());
        //Производим необходимые проверки
        isValidSize(points.length);

        //Устанавливаем значения полей
        _pointsCount = points.length;
        _points = new FunctionPoint[_pointsCount];

        //Копируем массив точек
        double prev_x = points[0].getX() - 1;
        for (int i = 0; i < getPointsCount(); ++i) {
            disorderedAbscissa(prev_x, points[i].getX());
            _points[i] = points[i].clone();
            prev_x = points[i].getX();
        }
    }


    // ===========================  Публичные методы доступа  ============================

    @Override
    public int getPointsCount() {
        return _pointsCount;
    }

    @Override
    public FunctionPoint getPoint(int index) {
        isValidIndex(index);
        return new FunctionPoint(_points[index]);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        //Проверка валидности индекса
        isValidIndex(index);
        //Проверка корректности в соответствии с соседними значениями абсцисс
        outsideOfDefinition(index, point);
        //Замена текущей точки на новую
        _points[index] = point.clone();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (_pointsCount == _points.length) reallocate();

        int place = getPlaceForPoint(point);

        System.arraycopy(_points, place, _points, place + 1, _pointsCount - place);
        _points[place] = point.clone();
        ++_pointsCount;
    }

    @Override
    public void deletePoint(int index) {
        isValidIndex(index);
        isMinSize();
        System.arraycopy(_points, index + 1, _points, index, _pointsCount - index - 1);
        _points[_pointsCount - 1] = null;
        --_pointsCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayTabulatedFunction array)
            return Arrays.equals(_points, array._points);
        else
            return super.equals(obj);
    }

    @Override
    public TabulatedFunction clone() {
        ArrayTabulatedFunction clone = (ArrayTabulatedFunction) super.clone();
        clone._points = new FunctionPoint[_pointsCount];
        for (int i = 0; i < getPointsCount(); ++i) {
            clone._points[i] = getPoint(i).clone();
        }
        return clone;
    }
    // ===============================  Приватные методы  ===============================

    /**
     * Пересоздаёт массив точек
     */
    private void reallocate() {
        FunctionPoint[] buff = new FunctionPoint[(int) (max(_points.length, 1) * REALLOCATE_MULTIPLIER)];

        System.arraycopy(_points, 0, buff, 0, _pointsCount);
        _points = buff;
    }

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {

        @Override
        public ArrayTabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public ArrayTabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public ArrayTabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new ArrayTabulatedFunction(points);
        }
    }
}
