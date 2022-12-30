package com.example.lab_7.functions;

import java.io.*;
import javax.xml.crypto.NoSuchMechanismException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс для упрощённой работы с табулированной функцией
 */
@SuppressWarnings("unused")
final public class TabulatedFunctions {

    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    /**
     * Запрещаем создавать экземпляр данного класса
     */
    private TabulatedFunctions() {
    }

    /**
     * Метод создающий табулированную функцию на основе полученной функции
     *
     * @param function    функция переданная для разложения в табулированную
     * @param leftX       левая граница табулированной функции
     * @param rightX      правая граница табулированной функции
     * @param pointsCount количество точек на заданном участке
     * @return объект табулированной функции
     * @throws IllegalArgumentException выбрасывается в случае если границы не входят в область определения
     * @see TabulatedFunction
     * @see Function
     */
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        return createTabulatedFunction(privateTabulate(function, leftX, rightX, pointsCount));
    }

    /**
     * Создаёт массив точек
     *
     * @param function    функция для табулирования
     * @param leftX       левая граница табуляции
     * @param rightX      правая граница табуляции
     * @param pointsCount количество точек
     * @return массив точек {@link FunctionPoint}
     */
    private static FunctionPoint[] privateTabulate(Function function, double leftX, double rightX, int pointsCount) {
        //Проверка не выходим ли за границы области определения функции
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException("The specified bounds are outside the scope of the function");

        //Создаём массив точек
        FunctionPoint[] points = new FunctionPoint[pointsCount];

        //Получаем массив, равномерно распределённых на заданном участке, значений по оси абсцисс
        double[] xValues = AbstractTabulatedFunction.xRange(leftX, rightX, pointsCount);

        //Наконец заполняем массив точек полученными координатами точек
        for (int i = 0; i < pointsCount; i++)
            points[i] = new FunctionPoint(xValues[i], function.getFunctionValue(xValues[i]));

        //Возвращаем полученный массив точек
        return points;
    }

    /**
     * Метод записывает в поток: количество точек, а также их значения (координаты)
     *
     * @param function функция для вывода в поток
     * @param out      поток для записи
     * @throws IOException исключения потока
     * @see TabulatedFunction
     * @see Function
     */
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out)
            throws IOException {
        //Создаём буферный поток
        DataOutputStream dataOut = new DataOutputStream(out);

        //Записываем кол-во точек
        dataOut.writeInt(function.getPointsCount());

        //Записываем координаты точек
        for (int i = 0; i < function.getPointsCount(); ++i) {
            dataOut.writeDouble(function.getPointX(i));
            dataOut.writeDouble(function.getPointY(i));
        }

        //Сбрасываем буфер в поток
        dataOut.flush();
    }

    /**
     * Метод считывает с потока: количество и значения точек, записывая их в табулированную функцию
     *
     * @param in поток для считывания
     * @return табулированная функция
     * @throws IOException исключения потока
     */
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        //Создаём поток
        DataInputStream dataIn = new DataInputStream(in);

        //Считываем кол-во точек и создаём для них массив соответствующей длины
        int length = dataIn.readInt();
        FunctionPoint[] points = new FunctionPoint[length];

        //Считываем и записываем в массив координаты точек
        for (int i = 0; i != points.length; ++i) {
            points[i] = new FunctionPoint(dataIn.readDouble(), dataIn.readDouble());
        }

        //Возвращаем объект табулированной функции
        return new ArrayTabulatedFunction(points);
    }


    /**
     * Метод записывает табулированную функцию в символьный поток
     *
     * @param function табулированная функция
     * @param out      поток для записи
     */
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {

        PrintWriter output = new PrintWriter(new BufferedWriter(out));

        output.print(function.getPointsCount());

        for (int i = 0; i < function.getPointsCount(); ++i) {
            output.print(" " + function.getPointX(i) + " " + function.getPointY(i));
        }

        output.flush();
    }

    /**
     * Метод считывает табулированную функцию с символьного потока
     *
     * @param in поток для считывания
     * @return табулированную функцию
     * @throws IOException исключения потока
     */
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {

        StreamTokenizer tokenizer = new StreamTokenizer(in);

        tokenizer.parseNumbers();
        tokenizer.nextToken();

        FunctionPoint[] values = new FunctionPoint[(int) tokenizer.nval];
        for (int i = 0; i != values.length; ++i) {
            tokenizer.nextToken();
            double x = tokenizer.nval;

            tokenizer.nextToken();
            double y = tokenizer.nval;

            values[i] = new FunctionPoint(x, y);
        }
        return new ArrayTabulatedFunction(values);
    }

    /**
     * Устанавливает выбранную реализацию табулированной функции
     *
     * @param functionFactory выбранная реализация табулированной функции
     */
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory) {
        factory = functionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> c,
                                                            double leftX,
                                                            double rightX,
                                                            int pointsCount) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes.length == 3 && classes[0].equals(Double.TYPE) && classes[1].equals(Double.TYPE) && classes[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, pointsCount);
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException |
                         InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> c,
                                                            double leftX,
                                                            double rightX,
                                                            double[] values) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes.length == 3 && classes[0].equals(Double.TYPE) && classes[1].equals(Double.TYPE) && classes[2].equals(values.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, values);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> c,
                                                            FunctionPoint[] points) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes[0].equals(points.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(new Object[]{points});
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }


    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> c, Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException();
        double[] values = new double[pointsCount];
        double argument = leftX;
        for (int i = 0; i < pointsCount; i++) {
            values[i] = function.getFunctionValue(argument);
            argument += (rightX - leftX) / (pointsCount - 1);
        }
        return createTabulatedFunction(c, leftX, rightX, values);
    }
}