package com.example.lab_7.functions;

import com.example.lab_7.functions.exceptions.FunctionPointIndexOutOfBoundsException;
import com.example.lab_7.functions.exceptions.InappropriateFunctionPointException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    // ====================================  Поля  ========================================
    /**
     * Минимальное и максимальное кол-во точек в функции
     */
    public final static int MIN_SIZE = 2, MAX_SIZE = 10000;
    /**
     * Левая граница функции
     */
    protected double _leftX;
    /**
     * Правая граница функции
     */
    protected double _rightX;

    // ================================  Конструкторы  ===================================
    public AbstractTabulatedFunction(double leftX, double rightX) {
        isValidBorders(leftX, rightX);
        _leftX = leftX;
        _rightX = rightX;
    }


    // ==============================  Публичные методы   ================================

    /**
     * Возвращает массив точек равномерно распределённых на заданном участке
     *
     * @param leftX      левая граница
     * @param rightX     правая граница
     * @param pointCount кол-во точек на заданном интервале
     * @return массив точек
     */
    public static double[] xRange(double leftX, double rightX, int pointCount) {
        //Вычисляем шаг с которым нужно равномерно расставить значения функции
        //Делим расстояние между границами на кол-во интервалов
        double[] roll = new double[pointCount];
        double step = (rightX - leftX) / (pointCount - 1);
        double x = leftX;
        for (int i = 0; i < pointCount; i++, x += step) {
            roll[i] = x;
        }
        return roll;
    }

    @Override
    public double getLeftDomainBorder() {
        return _leftX;
    }

    /**
     * Установить значение левой границе функции
     *
     * @param leftX новое значение левой границы
     */
    protected void setLeftDomainBorder(double leftX) {
        isValidBorders(leftX, getRightDomainBorder());
        this._leftX = leftX;
    }

    @Override
    public double getRightDomainBorder() {
        return _rightX;
    }

    /**
     * Установить значение правой границе функции
     *
     * @param rightX новое значение правой границы
     */
    protected void setRightDomainBorder(double rightX) {
        isValidBorders(getLeftDomainBorder(), rightX);
        this._rightX = rightX;
    }

    @Override
    public double getFunctionValue(double x) {
        //Случай когда x не входит в область определения
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())
            return Double.NaN;

        for (int i = 0; i < getPointsCount(); ++i) {
            //Случай если для данного x известно точное значение функции
            if (x == getPointX(i))
                return getPointY(i);

            //Случай когда значение не известно
            if (x < getPointX(i))
                return getLinearFunctionValue(getPoint(i - 1), getPoint(i), x);
        }
        return Double.NaN;
    }

    /**
     * Вернёт индекс последнего элемента
     *
     * @return индекс последнего элемента
     */
    public int getEndIndex() {
        return getPointsCount() - 1;
    }


    @Override
    public String toString() {
        String begin = "{", end = "}", sep = ", ";
        StringBuilder str = new StringBuilder(begin);
        if (getPointsCount() > 0) str.append(getPoint(0));

        for (int i = 1; i < getPointsCount(); ++i) str.append(sep).append(getPoint(i));
        return str + end;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TabulatedFunction other)) return false;
        if (getPointsCount() != other.getPointsCount()) return false;

        //Сравнение всех точек в текущем наборе с точками другого набора точек
        for (int i = 0; i < getPointsCount(); ++i)
            if (!getPoint(i).equals(other.getPoint(i))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + getPointsCount();
        for (FunctionPoint point : this)
            result = 31 * result + point.hashCode();
        return result;
    }

    @Override
    public TabulatedFunction clone() {
        try {
            return (TabulatedFunction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getPointsCount();
            }

            @Override
            public FunctionPoint next() {
                if (hasNext()) return getPoint(index++);
                else throw new NoSuchElementException("Iterator at the end");
            }
        };
    }

    /**
     * Метод проверяет корректность границ функции
     *
     * @param leftX  левая граница
     * @param rightX правая граница
     */
    protected void isValidBorders(double leftX, double rightX) {
        if (leftX >= rightX)
            throw new IllegalArgumentException("Illegal border");
    }

    /**
     * Выполняет проверку кол-ва точек
     *
     * @param pointsCount кол-во точек
     */
    protected void isValidSize(double pointsCount) {
        if (pointsCount < MIN_SIZE)
            throw new IllegalArgumentException("Illegal size");
    }

    /**
     * Проверяет соблюдение сортировки точек по оси абсцисс
     *
     * @param points массив точек {@link FunctionPoint}
     */
    public void isValidSort(FunctionPoint[] points) {
        for (int i = 1; i < points.length; ++i)
            disorderedAbscissa(points[i - 1].getX(), points[i].getX());
    }


    /**
     * Проверяет на нарушение сортировки для подряд идущих точек по оси абсцисс
     *
     * @param prev_x предыдущее значение абсциссы
     * @param cur_x  текущее значение абсциссы
     */
    protected void disorderedAbscissa(double prev_x, double cur_x) {
        if (prev_x >= cur_x)
            throw new IllegalArgumentException("Illegal sort");
    }

    /**
     * Метод для проверки корректности индекса точки
     *
     * @param index индекс точки
     */
    protected void isValidIndex(int index) {
        if (0 > index || index >= getPointsCount())
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
    }

    /**
     * Проверяет не достигли ли мы минимального кол-ва точек
     * <p>
     * Метод для проверки кол-ва элементов, вызывается перед удалением точки.
     */
    protected void isMinSize() {
        int size = getPointsCount();
        assert size >= MIN_SIZE; //TODO: удалить строку если всё в порядке
        if (size == MIN_SIZE) throw new IllegalStateException("The size is too small");
    }

    /**
     * Метод для проверки входит ли точка в диапазон соседних для неё точек по оси абсцисс
     *
     * @param index индекс точки
     * @param point проверяемая точка {@link FunctionPoint}
     * @throws InappropriateFunctionPointException выбрасывается если точка не в диапазоне
     */
    protected void outsideOfDefinition(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        //Определим левую и правую границу для текущей точки
//        double leftBound = (index == 0 ? _leftX : getPointX(index - 1));
//        double rightBound = (index == getEndIndex() ? _rightX : getPointX(index + 1));
        double leftBound = (index == 0 ? Double.NEGATIVE_INFINITY : getPointX(index - 1));
        double rightBound = (index == getEndIndex() ? Double.POSITIVE_INFINITY : getPointX(index + 1));

        //Выполним проверку: входит ли точка в указанный диапазон
        if ((point.getX() <= leftBound || point.getX() >= rightBound))
            throw new InappropriateFunctionPointException("Point not in diapason");
    }


    /**
     * Сравнивает абсциссы двух точек
     *
     * @param first  первая точка
     * @param second вторая точка
     * @throws InappropriateFunctionPointException когда у точек совпадает абсцисса
     */
    protected void isDuplicateAbscissa(FunctionPoint first, FunctionPoint second) throws InappropriateFunctionPointException {
        if (first.getX() == second.getX())
            throw new InappropriateFunctionPointException("You already have this point x");
    }

    /**
     * Находит место для вставки новой точки
     *
     * @param point точка {@link FunctionPoint}, для которой ищем место
     * @return индекс места для вставки новой точки
     */
    protected int getPlaceForPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        for (int i = getEndIndex(); i >= 0; --i) {
            isDuplicateAbscissa(point, getPoint(i));

            if (point.getX() > getPointX(i))
                return i + 1;
        }
        return 0;
    }

    /**
     * Получить значение функции для заданной абсциссы по линейной интерполяции
     *
     * @param firstPoint  первая точка
     * @param secondPoint вторая
     * @param x           значение абсциссы для которой рассчитывается значение
     * @return Значение функции в точке по линейной интерполяции
     */
    protected double getLinearFunctionValue(FunctionPoint firstPoint, FunctionPoint secondPoint, double x) {
        double x1 = firstPoint.getX();
        double y1 = firstPoint.getY();

        double x2 = secondPoint.getX();
        double y2 = secondPoint.getY();

        return ((x - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }
}
