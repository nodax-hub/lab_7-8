package com.example.lab_7;

import com.example.lab_7.functions.*;
import com.example.lab_7.functions.basic.Cos;
import com.example.lab_7.functions.basic.Exp;
import com.example.lab_7.functions.basic.Log;
import com.example.lab_7.functions.basic.Sin;
import com.example.lab_7.threads.*;

@SuppressWarnings("unused")
public class Main {
    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws Exception {
        test_lab_7();
        test_lab_8();
    }

    private static void test_lab_8() {
        test_iterator();
        test_fabric();
        test_reflex();
    }

    private static void test_reflex() {
        TabulatedFunction f;

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f + "\n");

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10});
        System.out.println(f.getClass());
        System.out.println(f + "\n");

        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[]{
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );

        System.out.println(f.getClass());
        System.out.println(f + "\n");

        f = TabulatedFunctions.tabulate(
                LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f + "\n");
    }

    private static void test_fabric() {
        Function f = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        TabulatedFunctions.setTabulatedFunctionFactory(new
                LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());

        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        TabulatedFunctions.setTabulatedFunctionFactory(new
                ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());

        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
    }

    private static void test_iterator() {
        TabulatedFunction f = TabulatedFunctions.createTabulatedFunction(0, 10, 10);
        for (FunctionPoint p : f) {
            System.out.println(p);
        }
    }

    private static void test_lab_7() throws InterruptedException {
        task1();
        task2();
        task3();
        task4();
    }

    private static void task4() throws InterruptedException {
        System.out.println("Задание 4");
        complicatedThreads();
    }

    private static void task3() {
        System.out.println("Задание 3");
        simpleThreads();
    }

    private static void task2() {
        System.out.println("Задание 2");
        nonThread();
    }

    private static void task1() {
        System.out.println("Задание 1");
        System.out.println("Теоретическое значение " + (Math.E - 1));
        final double step = 0.001;
        System.out.println(Functions.integrate(new Exp(), 0, 1, step));
        System.out.println("Шаг = " + step + "\n");
    }

    private static void nonThread() {
        Task task = new Task(100);

        for (int i = 0; i < task.getTasks(); ++i) {
            task.setFunction(new Log(1 + (Math.random() * 9)));

            task.setLeftX(Math.random() * 100);
            task.setRightX(Math.random() * 100 + 100);

            task.setSamplingStep(Math.random());

            System.out.println("Source leftX = " + task.getLeftX() +
                    " rightX = " + task.getRightX() +
                    " step = " + task.getSamplingStep());

            double res = Functions.integrate(task.getFunction(),
                    task.getLeftX(),
                    task.getRightX(),
                    task.getSamplingStep());

            System.out.println("Result leftX = " + task.getLeftX() +
                    " rightX = " + task.getRightX() +
                    " step = " + task.getSamplingStep() +
                    " integrate = " + res);
        }
    }

    private static void simpleThreads() {
        Task task = new Task(100);

        Thread generator = new Thread(new SimpleGenerator(task));
        Thread integrator = new Thread(new SimpleIntegrator(task));

        generator.start();
        integrator.start();
    }

    private static void complicatedThreads() throws InterruptedException {
        Task task = new Task(100);

        Semaphore semaphore = new Semaphore();

        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);

        generator.start();
        integrator.start();

        Thread.sleep(50);

        generator.interrupt();
        integrator.interrupt();
    }
}
