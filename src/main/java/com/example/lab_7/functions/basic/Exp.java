package com.example.lab_7.functions.basic;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Exp implements Function {

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getFunctionValue(double x) {
        return Math.exp(x);
    }
}