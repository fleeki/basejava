package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static final Lock lock = new ReentrantLock();
    private static final AtomicInteger atomicCount = new AtomicInteger();
    private static int counter;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        System.out.println(Thread.currentThread().getName());
//        Thread thread0 = new Thread() {
//            @Override
//            public void run() {
//                System.out.println(getName() + ", " + getState());
//            }
//        };
//        thread0.start();
//        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", "
//                + Thread.currentThread().getState())).start();
//        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });
            completionService.poll();
//            System.out.println(future.isDone());
//            System.out.println(future.get());

//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//            thread.start();
//            threads.add(thread);
        }

//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(counter);
        System.out.println(atomicCount.get());

//        System.out.println("===============Answer HW11===============");
//        final String lock1 = "lock1";
//        final String lock2 = "lock2";
//        deadlock(lock1, lock2);
//        deadlock(lock2, lock1);
    }

//    private synchronized void inc() {
//        counter++;
//    }

//    private void inc() {
//        lock.lock();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//    }

    private void inc() {
        atomicCount.incrementAndGet();
    }

    private static void deadlock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting: " + lock1);
            synchronized (lock1) {
                System.out.println("Holding: " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting: " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding: " + lock2);
                }
            }
        }).start();
    }
}