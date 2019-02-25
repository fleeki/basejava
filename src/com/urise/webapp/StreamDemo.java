package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StreamDemo {
    private static final int[] ARRAY_1 = {1, 2, 3, 3, 2, 3, 6, 8, 6, 4};
    private static final int[] ARRAY_2 = {9, 8};
    private static final List<Integer> INTEGERS = new ArrayList<>(Arrays.stream(ARRAY_1).boxed().collect(Collectors.toList()));

    public static void main(String[] args) {
        System.out.println(minValue(ARRAY_1));
        System.out.println(minValue(ARRAY_2));
        System.out.println(oddOrEven(INTEGERS));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, x) -> x += acc * 10);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final AtomicInteger sum = new AtomicInteger(0);
        return integers.stream().collect(Collectors.partitioningBy(x -> {
            sum.addAndGet(x);
            return x % 2 == 0;
        })).get(sum.get() % 2 != 0);
    }
}