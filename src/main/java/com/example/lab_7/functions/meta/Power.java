package com.example.lab_7.functions.meta;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Power implements Function {
    Function _base, _exp;

    public Power(Function base, Function exp) {
        _base = base;
        _exp = exp;
    }

    @Override
    public double getLeftDomainBorder() {
        return _base.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return _base.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(_base.getFunctionValue(x), _exp.getFunctionValue(x));
    }
}