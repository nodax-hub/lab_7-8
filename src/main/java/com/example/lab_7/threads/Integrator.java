package com.example.lab_7.threads;

import com.example.lab_7.functions.Functions;

public class Integrator extends Thread {
    private final Task tasks;
    private final Semaphore semaphore;
    private boolean isRun = false;

    public Integrator(Task t, Semaphore s) {
        tasks = t;
        semaphore = s;
    }

    public void run() {
        isRun = true;
        for (int i = 0; i < tasks.getTasks() && isRun; ++i) {
            try {
                semaphore.beginRead();
                System.out.println("Result leftX = " + tasks.getLeftX() +
                        " rightX = " + tasks.getRightX() +
                        " step = " + tasks.getSamplingStep() +
                        " integrate = " +
                        Functions.integrate(tasks.getFunction(),
                                tasks.getLeftX(),
                                tasks.getRightX(),
                                tasks.getSamplingStep()));

                semaphore.endRead();
            } catch (InterruptedException e) {
                System.out.println("Интегратор прервали во время сна," +
                        " он корректно завершил свою работу");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        isRun = false;
    }

}
