package main.java;

import java.util.concurrent.Semaphore;

public class H2O {
    public H2O() {

    }
    private Semaphore hydrogen = new Semaphore(2);
    private Semaphore oxygen = new Semaphore(0);
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        hydrogen.acquire();
        releaseHydrogen.run();
        oxygen.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygen.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        hydrogen.release(2);
    }
    public static void main(String[] args) {
        H2O h2O = new H2O();

        new Thread(() -> {
            try {
                h2O.oxygen(() -> System.out.print("O"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2O.oxygen(() -> System.out.print("O"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2O.hydrogen(() -> System.out.print("H"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2O.hydrogen(() -> System.out.print("H"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2O.hydrogen(() -> System.out.print("H"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                h2O.hydrogen(() -> System.out.print("H"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
