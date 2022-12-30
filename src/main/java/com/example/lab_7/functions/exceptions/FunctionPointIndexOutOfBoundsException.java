package com.example.lab_7.functions.exceptions;

/**
 * Исключение выхода за границы набора точек при обращении к ним по номеру
 */
@SuppressWarnings("unused")
public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public FunctionPointIndexOutOfBoundsException() {
        super();
    }

    public FunctionPointIndexOutOfBoundsException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
