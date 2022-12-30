package com.example.lab_7.ui.exceptions;

public class FileIsNotAssignedException extends RuntimeException {
    public FileIsNotAssignedException() {
    }

    public FileIsNotAssignedException(String massage) {
        super(massage);
    }
}
