package com.example.lab_7.ui;

import com.example.lab_7.functions.FunctionPoint;

public class FunctionPointT {
    private final FunctionPoint point;

    public FunctionPointT(FunctionPoint point) {
        this.point = point;
    }

    public FunctionPointT(double x, double y) {
        this(new FunctionPoint(x, y));
    }

    public Double getX() {
        return point.getX();
    }

    public void setX(Double x) {
        point.setX(x);
    }

    public Double getY() {
        return point.getY();
    }

    public void setY(Double y) {
        point.setY(y);
    }

    @Override
    public String toString() {
        return point.toString();
    }
}
