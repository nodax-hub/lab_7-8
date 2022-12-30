package com.example.lab_7.threads;


import com.example.lab_7.functions.Function;

@SuppressWarnings("unused")
public class Task {
    private final int tasks;
    private Function function;
    private double leftX, rightX, samplingStep;

    public Task(int tasks) {
        this.tasks = tasks;
    }

    public int getTasks() {
        return tasks;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        this.rightX = rightX;
    }

    public double getSamplingStep() {
        return samplingStep;
    }

    public void setSamplingStep(double samplingStep) {
        this.samplingStep = samplingStep;
    }
}
