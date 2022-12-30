package com.example.lab_7.functions.exceptions;

/**
 * Исключение, выбрасываемое при попытке добавления или изменения точки функции несоответствующим образом
 */
@SuppressWarnings("unused")
public class InappropriateFunctionPointException extends Exception {
    public InappropriateFunctionPointException() {
        super();
    }

    public InappropriateFunctionPointException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
