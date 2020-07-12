package main.java;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    public DiningPhilosophers() {

    }
    private Lock[] locks = new Lock[]{
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()};
    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        Lock left = locks[philosopher], right = locks[(philosopher + 1) % 5];
        while (true) {
            while (!left.tryLock()) {
                Thread.yield();
            }

            if (right.tryLock()) {
                break;
            } else {
                left.unlock();
            }
            Thread.yield();
        }

        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        left.unlock();
        right.unlock();
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    diningPhilosophers.wantsToEat(finalI,
                            () -> System.out.print(" pickLeftFork "),
                            () -> System.out.print(" pickRightFork "),
                            () -> System.out.print(" eat "),
                            () -> System.out.print(" putLeftFork "),
                            () -> System.out.print(" putRightFork "));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
