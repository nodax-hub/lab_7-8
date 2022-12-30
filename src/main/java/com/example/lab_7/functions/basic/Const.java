package com.example.lab_7.functions.basic;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Const implements Function {
    private final double _value;

    public Const(double c) {
        _value = c;
    }

    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double c) {
        return _value;
    }
}