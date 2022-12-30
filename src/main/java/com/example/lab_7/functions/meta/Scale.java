package com.example.lab_7.functions.meta;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Scale implements Function {
    Function _function;
    double _scale_x, _scale_y;

    public Scale(Function function, double scale_x, double scale_y) {
        _function = function;
        _scale_x = scale_x;
        _scale_y = scale_y;
    }

    @Override
    public double getLeftDomainBorder() {
        return _function.getLeftDomainBorder() * _scale_x;
    }

    @Override
    public double getRightDomainBorder() {
        return _function.getRightDomainBorder() * _scale_x;
    }

    @Override
    public double getFunctionValue(double x) {
        return _function.getFunctionValue(x * _scale_x) * _scale_y;
    }
}