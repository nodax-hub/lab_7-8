package com.example.lab_7.threads;


import com.example.lab_7.functions.basic.Log;

public class Generator extends Thread {

    private final Task task;
    private final Semaphore semaphore;
    private boolean isRun = false;

    public Generator(Task t, Semaphore s) {
        task = t;
        semaphore = s;
    }

    public void run() {
        isRun = true;
        for (int i = 0; i < task.getTasks() && isRun; ++i) {
            try {
                Log log = new Log(1 + (Math.random() * 9));
                semaphore.beginWrite();
                SimpleGenerator.write(log, task);
                semaphore.endWrite();

            } catch (InterruptedException e) {
                System.out.println("Генератор прервали во время сна," +
                        "он корректно завершил свою работу");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        isRun = false;
    }
}
