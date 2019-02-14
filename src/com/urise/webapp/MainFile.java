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

        System.out.println("\n====================HW8====================");
        File dir = new File("D:\\Java");
        StringBuilder paragraph = new StringBuilder();
        printAllFileNames(dir, paragraph);
    }

    private static void printAllFileNames(File dir, StringBuilder paragraph) {
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                System.out.print(paragraph.toString());
                paragraph.append("\t");
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    printAllFileNames(file, paragraph);
                } else {
                    System.out.println("File: " + file.getName());
                }
                paragraph = paragraph.deleteCharAt(paragraph.length()-1);
            }
        }
    }
}