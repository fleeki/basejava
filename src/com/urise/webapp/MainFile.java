package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        System.out.println("==========HW8==========");
        File dir = new File(".\\src");
        printAllFileNames(dir);
    }

    private static void printAllFileNames(File dir) {
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    printAllFileNames(file);
                } else {
                    System.out.println("File: " + file.getName());
                }
            }
        }
    }
}