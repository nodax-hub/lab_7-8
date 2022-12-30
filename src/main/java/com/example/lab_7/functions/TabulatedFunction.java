package com.example.lab_7.functions;

import com.example.lab_7.functions.exceptions.FunctionPointIndexOutOfBoundsException;
import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;

/**
 * Табулированная функция - функция заданная набором точек с определённым шагом по оси абсцисс
 *
 * @see Function
 * @see FunctionPoint
 * @see ArrayTabulatedFunction
 * @see LinkedListTabulatedFunction
 */
@SuppressWarnings("unused")
public interface TabulatedFunction extends Function, Iterable<FunctionPoint> {
    /**
     * Возвращает кол-во точек
     */
    int getPointsCount();

    /**
     * Получение точки
     *
     * @param index индекс в массиве точек
     * @return ссылку на копию точки
     */
    FunctionPoint getPoint(int index);

    /**
     * Получение значения по оси абсцисс точки по указанному индексу
     *
     * @param index индекс точки, в массиве точек
     * @return значение точки по оси абсцисс
     */
    default double getPointX(int index) {
        return getPoint(index).getX();
    }


    /**
     * Получение значения по оси ординат точки по указанному индексу
     *
     * @param index индекс точки, в массиве точек
     * @return значение точки по оси ординат
     */
    default double getPointY(int index) {
        return getPoint(index).getY();
    }


    @Override
    default double getLeftDomainBorder() {
        return getPointX(0);
    }

    @Override
    default double getRightDomainBorder() {
        return getPointX(getPointsCount() - 1);
    }

    /**
     * Устанавливает новые координаты точки под указанным индексом
     *
     * @param index индекс точки в массиве
     * @param point точка на которую будет заменено текущее значение
     * @implNote Замена не будет произведена в случае если значение новой точки по x не входит в диапазон её соседей
     */
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;


    /**
     * Устанавливает новые координаты точки под указанным индексом по оси абсцисс
     *
     * @param index индекс точки в массиве
     * @param value новое значение для точки под указанным индексом по оси абсцисс
     * @implNote Замена не будет произведена в случае если значение новой точки по x не входит в диапазон её соседей
     */
    default void setPointX(int index, double value) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        setPoint(index, new FunctionPoint(value, getPointY(index)));
    }

    /**
     * Устанавливает новые координаты точки под указанным индексом по оси ординат
     *
     * @param index индекс точки в массиве
     * @param value новое значение для точки под указанным индексом по оси ординат
     */
    default void setPointY(int index, double value) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        setPoint(index, new FunctionPoint(getPointX(index), value));
    }

    /**
     * Добавляет новую точку в массив точек
     *
     * @param point точка, которую следует добавить
     * @implNote место точки в массиве определяет её значение по иксу
     */
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    /**
     * Удаление точки из массива
     *
     * @param index индекс удаляемой точки
     */
    void deletePoint(int index);

    /**
     * Проверяет если ли точки в функции
     *
     * @return ответ (пуст или нет)
     */
    default boolean isEmpty() {
        return getPointsCount() == 0;
    }

    /**
     * Текстовое представление
     *
     * @return табулированная функция в строку
     */
    String toString();

    /**
     * Поддерживается глубокое копирование табулированных функций
     *
     * @return клон табулированной функции
     */
    TabulatedFunction clone();


}
