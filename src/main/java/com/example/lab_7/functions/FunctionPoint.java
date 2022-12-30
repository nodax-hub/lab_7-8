package com.example.lab_7.functions;


import java.io.Serializable;

/**
 * Класс описывающий точку на координатной плоскости
 *
 * @author NODAX
 */
@SuppressWarnings("unused")
public class FunctionPoint implements Serializable, Cloneable {
    private double _x, _y;

    /**
     * Конструктор по умолчанию создаст точку с координатами (0,0)
     */
    public FunctionPoint() {
        this(0, 0);
    }

    /**
     * Конструктор принимающий координаты точки
     */
    public FunctionPoint(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Конструктор копирования
     */
    FunctionPoint(FunctionPoint point) {
        this(point.getX(), point.getY());
    }


    /**
     * Получить значение точки по оси абсцисс
     *
     * @return значение точки по оси абсцисс
     */
    public double getX() {
        return _x;
    }

    /**
     * Установить значение точки по оси абсцисс
     *
     * @param value новое значение
     */
    public void setX(double value) {
        _x = value;
    }

    /**
     * Получить значение точки по оси ординат
     *
     * @return значение точки по оси ординат
     */
    public double getY() {
        return _y;
    }

    /**
     * Установить значение точки по оси ординат
     *
     * @param value новое значение
     */
    public void setY(double value) {
        _y = value;
    }

    /**
     * Возвращает строковое представление объекта.
     *
     * @return символьное представление объекта
     */
    @Override
    public String toString() {
        return String.format("(%.1f; %.1f)", _x, _y).replace(',', '.');
    }

    /**
     * Проверка на точное сравнение
     *
     * @param obj объект с которым сравниваем точку
     * @return истину если переданный объект является {@link FunctionPoint} и координаты совпадают иначе ложь
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof FunctionPoint fp && (this == fp || getX() == fp.getX() && getY() == fp.getY());
    }

    /**
     * Возвращает хэш код объекта
     * В случае когда хэш коды объектов различны - объекты различны
     *
     * @return хэш код данного объекта
     */
    @Override
    public int hashCode() {
        int result = 1;
        long temp = Double.doubleToLongBits(_x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(_y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    /**
     * Метод создаёт глубокую копию объекта
     *
     * @return клон объекта
     */
    @Override
    public FunctionPoint clone() {
        try {
            return (FunctionPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error of JVM");
        }
    }
}