package com.example.lab_7.functions.meta;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Mult implements Function {

    Function _first, _second;

    public Mult(Function first, Function second) {
        _first = first;
        _second = second;
    }

    @Override
    public double getLeftDomainBorder() {
        double maxLeftDomainBorder = Math.max(_first.getLeftDomainBorder(), _second.getLeftDomainBorder());
        double minRightDomainBorder = Math.min(_first.getRightDomainBorder(), _second.getRightDomainBorder());

        return minRightDomainBorder < maxLeftDomainBorder ? Double.NaN : maxLeftDomainBorder;
    }

    @Override
    public double getRightDomainBorder() {
        double maxLeftDomainBorder = Math.max(_first.getLeftDomainBorder(), _second.getLeftDomainBorder());
        double minRightDomainBorder = Math.min(_first.getRightDomainBorder(), _second.getRightDomainBorder());

        return minRightDomainBorder < maxLeftDomainBorder ? Double.NaN : minRightDomainBorder;
    }

    @Override
    public double getFunctionValue(double x) {
        return _first.getFunctionValue(x) * _second.getFunctionValue(x);
    }
}