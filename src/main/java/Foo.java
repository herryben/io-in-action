package main.java;

public class Foo {
    public Foo() {

    }
    private int state = 0;
    public void first(Runnable printFirst) throws InterruptedException {
        while (state != 0) {
            Thread.yield();
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        state = 1;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (state != 1) {
            Thread.yield();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        state = 2;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (state != 2) {
            Thread.yield();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        state = 3;
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        new Thread(() -> {
            try {
                foo.third(() -> {
                    System.out.println("three");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                foo.second(() -> {
                    System.out.println("two");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                foo.first(() -> {
                    System.out.println("one");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
