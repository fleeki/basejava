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
        for (int i = 0; i < 10000; i++) {
            transfer(account1, account2, random.nextInt(100));
        }
    }

    public void secondThread() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            transfer(account2, account1, random.nextInt(100));
        }
    }

    public void finished() {
        System.out.println("Balance account1: " + account1.getBalance());
        System.out.println("Balance account2: " + account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
    }

    public void transfer(Account giver, Account receiving, int amount) {
        synchronized (account1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (account2) {
                giver.withdraw(amount);
                receiving.deposit(amount);
            }
        }
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
}