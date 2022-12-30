package com.example.lab_7.threads;


import com.example.lab_7.functions.basic.Log;

public class SimpleGenerator implements Runnable {
    private final Task task;

    public SimpleGenerator(Task t) {
        task = t;
    }

    public void run() {
        for (int i = 0; i < task.getTasks(); ++i) {
            synchronized (task) {
                write(new Log(1 + (Math.random() * 9)), task);
            }
        }
    }

    public static void write(Log l, Task t) {
        t.setFunction(l);
        t.setLeftX(Math.random() * 100);
        t.setRightX(Math.random() * 100 + 100);
        t.setSamplingStep(Math.random());
        System.out.println("Source leftX = " + t.getLeftX() +
                " rightX = " + t.getRightX() +
                " step = " + t.getSamplingStep());
    }
}
