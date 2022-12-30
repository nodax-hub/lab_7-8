package com.example.lab_7.functions.basic;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Log implements Function {

    private final double _base;

    public Log(double base) throws IllegalArgumentException {
        if (base <= 0 || base == 1)
            throw new IllegalArgumentException("The base of a logarithm cannot be <= 0 or equal 1");
        _base = base;
    }

    public Log() {
        _base = Math.E;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getLeftDomainBorder() {
        return Double.MIN_NORMAL;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x) / Math.log(_base);
    }
}
