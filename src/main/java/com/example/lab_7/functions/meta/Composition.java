package com.example.lab_7.functions.meta;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Composition implements Function {
    Function _external, _internal;

    public Composition(Function external, Function internal) {
        _external = external;
        _internal = internal;
    }

    @Override
    public double getLeftDomainBorder() {
        return _external.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return _external.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return _external.getFunctionValue(_internal.getFunctionValue(x));
    }
}