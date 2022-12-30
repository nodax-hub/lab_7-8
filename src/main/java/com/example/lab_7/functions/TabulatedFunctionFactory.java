package com.example.lab_7.functions;

public interface TabulatedFunctionFactory {
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount);

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values);

    TabulatedFunction createTabulatedFunction(FunctionPoint[] points);
}
