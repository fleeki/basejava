package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate dateFormat(String date) {
        if ("сейчас".equals(date)) {
            return NOW;
        }

        String[] params = date.trim().toLowerCase().split("/");
        if (params.length != 2) {
            throw new IllegalArgumentException(date + " - invalid date format");
        } else {
            int month = Integer.parseInt(params[0]);
            int year = Integer.parseInt(params[1]);
            return of(year, Month.of(month));
        }
    }
}