package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume("Name");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new Name");
        System.out.println(resume);
        System.out.println("==========================");

        try {
            Method method = resume.getClass().getMethod("toString");
            System.out.println(method.invoke(resume));
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}