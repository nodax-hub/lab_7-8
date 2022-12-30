package com.example.lab_7.functions.basic;

import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
abstract class TrigonometricFunction implements Function {
    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }
}