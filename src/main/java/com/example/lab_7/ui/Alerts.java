package com.example.lab_7.ui;

import javafx.scene.control.Alert;


public class Alerts {
    private static final Alert illegalArgument = new Alert(Alert.AlertType.WARNING,
            "Неккоректые заданные аргументы !");
    private static final Alert unselectedPoint = new Alert(Alert.AlertType.WARNING,
            "Выберите точку для данной операции!");
    private static final Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
            "Внесённые вами изменения не были сохранены!");
    private static final Alert comingSoon = new Alert(Alert.AlertType.INFORMATION,
            "Данный функционал находится в разработке");
    private static final Alert indexOutOfBounds = new Alert(Alert.AlertType.ERROR,
            "Выход за границу дозволенного диапазона!");
    private static final Alert illegalState = new Alert(Alert.AlertType.WARNING,
            "Данная операция недопустима!");
    private static final Alert inappropriatePoint = new Alert(Alert.AlertType.WARNING,
            "Указанна неподходящая точка!");

    static {
        illegalArgument.setTitle("Некорректный аргумент");
        unselectedPoint.setTitle("Неизвестная цель");
        unsavedChanges.setTitle("Несохранённые изменения");
        comingSoon.setTitle("Уже скоро (возможно :D)");
        indexOutOfBounds.setTitle("Выход за границы");
        illegalState.setTitle("Невалидное состояние");
        inappropriatePoint.setTitle("Неуместная точка");

        illegalArgument.setHeaderText(null);
        unselectedPoint.setHeaderText(null);
        unsavedChanges.setHeaderText(null);
        comingSoon.setHeaderText(null);
        indexOutOfBounds.setHeaderText(null);
        illegalState.setHeaderText(null);
        inappropriatePoint.setHeaderText(null);
    }

    public static void showIllegalArgument() {
        illegalArgument.showAndWait();
    }

    public static void showUnselectedPoint() {
        unselectedPoint.showAndWait();
    }

    public static void showUnsavedChanges() {
        unsavedChanges.showAndWait();
    }

    public static void showComingSoon() {
        comingSoon.showAndWait();
    }

    public static void showIndexOutOfBounds() {
        indexOutOfBounds.showAndWait();
    }

    public static void showIllegalState() {
        illegalState.showAndWait();
    }

    public static void showInappropriatePoint() {
        inappropriatePoint.showAndWait();
    }

}
