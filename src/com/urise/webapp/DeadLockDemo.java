package com.urise.webapp;

import java.util.Random;

public class DeadLockDemo {
    public static void main(String[] args) {
        Operation operation = new Operation();

        Thread thread1 = new Thread(operation::firstThread);
        Thread thread2 = new Thread(operation::secondThread);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        operation.finished();
    }
}

class Operation {
    private final Account account1 = new Account();
    private final Account account2 = new Account();

    public void firstThread() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Account.transfer(account1, account2, random.nextInt(100));
        }
    }

    public void secondThread() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Account.transfer(account2, account1, random.nextInt(100));
        }
    }

    public void finished() {
        System.out.println("Balance account1: " + account1.getBalance());
        System.out.println("Balance account2: " + account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account giver, Account receiving, int amount) {
        System.out.println("In transfer(): " + Thread.currentThread().getName());
        synchronized (giver) {
            System.out.println("In first synchronized block: " + Thread.currentThread().getName());
            try {
                System.out.println("Sleep in first synchronized block: " + Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Wakeup in first synchronized block: " + Thread.currentThread().getName());
            synchronized (receiving) {
                System.out.println("In second synchronized block before withdraw(): " + Thread.currentThread().getName());
                giver.withdraw(amount);
                System.out.println("In second synchronized block after withdraw() but before deposit(): " + Thread.currentThread().getName());
                receiving.deposit(amount);
                System.out.println("In second synchronized block after deposit(): " + Thread.currentThread().getName());
            }
            System.out.println("In first synchronized block before second synchronized block: " + Thread.currentThread().getName());
        }
        System.out.println("In transfer() before first synchronized block: " + Thread.currentThread().getName());
    }
}