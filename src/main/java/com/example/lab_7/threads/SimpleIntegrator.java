package com.example.lab_7.threads;

import com.example.lab_7.functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task t) {
        task = t;
    }

    public void run() {
        for (int i = 0; i < task.getTasks(); ++i) {
            if (task.getFunction() == null) continue;
            synchronized (task) {
                System.out.println("Result leftX = " + task.getLeftX() +
                        " rightX = " + task.getRightX() +
                        " step = " + task.getSamplingStep() +
                        " integrate = " +
                        Functions.integrate(task.getFunction(),
                                task.getLeftX(),
                                task.getRightX(),
                                task.getSamplingStep()));
            }
        }
    }

}
