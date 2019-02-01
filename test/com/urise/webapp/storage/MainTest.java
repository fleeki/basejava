package com.urise.webapp.storage;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AllStorageTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println("Тест не пройден: " + failure);
        }
    }
}