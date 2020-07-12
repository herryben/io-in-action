package main.java;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class FizzBuzz {
    private int n;
    private int step = 1;
    private AtomicInteger state = new AtomicInteger(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true) {
            while (!state.compareAndSet(1, 0)) {
                LockSupport.parkNanos(1);
            }
            if (step > n) {
                state.set(1);
                return;
            }
            if (step % 3 == 0 && step % 5 != 0) {
                printFizz.run();
                step++;
            }
            state.set(1);
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true) {
            while (!state.compareAndSet(1, 0)) {
                LockSupport.parkNanos(1);
            }
            if (step > n) {
                state.set(1);
                return;
            }
            if (step % 3 != 0 && step % 5 == 0) {
                printBuzz.run();
                step++;
            }
            state.set(1);
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true) {
            while (!state.compareAndSet(1, 0)) {
                LockSupport.parkNanos(1);
            }
            if (step > n) {
                state.set(1);
                return;
            }
            if (step % 3 == 0 && step % 5 == 0) {
                printFizzBuzz.run();
                step++;
            }
            state.set(1);
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            while (!state.compareAndSet(1, 0)) {
                LockSupport.parkNanos(1);
            }
            if (step > n) {
                state.set(1);
                return;
            }
            if (step % 3 != 0 && step % 5 != 0) {
                printNumber.accept(step);
                step++;
            }
            state.set(1);
        }
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> {
                    System.out.println("fizz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> {
                    System.out.println("buzz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> {
                    System.out.println("fizzbuzz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.number(new IntConsumer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
