package com.example.lab_7.functions.meta;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Shift implements Function {
    Function _function;
    double _shift_x, _shift_y;

    public Shift(Function function, double shift_x, double shift_y) {
        _function = function;
        _shift_x = shift_x;
        _shift_y = shift_y;
    }

    @Override
    public double getLeftDomainBorder() {
        return _function.getLeftDomainBorder() + _shift_x;
    }

    @Override
    public double getRightDomainBorder() {
        return _function.getRightDomainBorder() + _shift_x;
    }

    @Override
    public double getFunctionValue(double x) {
        return _function.getFunctionValue(x + _shift_x) + _shift_y;
    }
}