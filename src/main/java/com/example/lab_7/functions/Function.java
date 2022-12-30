package com.example.lab_7.functions;

import java.io.Serializable;

/**
 * Функция одного параметра
 */
public interface Function extends Serializable, Cloneable {
    /**
     * Возвращает левую границу функции (наименьший x)
     */
    double getLeftDomainBorder();

    /**
     * Возвращает правую границу функции (наибольший x)
     */
    double getRightDomainBorder();

    /**
     * Возвращает значение функции в точке x
     */
    double getFunctionValue(double x);
}
