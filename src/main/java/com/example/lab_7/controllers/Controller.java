package com.example.lab_7.controllers;

import javafx.stage.Stage;

/**
 * Интерфейс контроллера
 */
public interface Controller {

    /**
     * Выполняет перерисовку (обновление данных)
     */
    default void redraw() {
    }

    /**
     * Устанавливает главную сцену
     *
     * @param stage новая сцена
     */
    void setStage(Stage stage);

    /**
     * Вернёт текущую сцену у данного контроллера
     *
     * @return текущая сцена
     */
    Stage getStage();

    /**
     * Обновляет заголовок главной сцены
     *
     * @param newTitle новое значение заголовка
     */
    default void updateTitle(String newTitle) {
        if (getStage() != null)
            getStage().setTitle(newTitle);
    }

}
