package com.norbertgogiel.toffee;

import java.time.LocalTime;
import java.util.IllegalFormatPrecisionException;

public class TimeParser {

    public LocalTime validateAndParse(String time) {
        String[] timeArr = time.split(":");
        if (timeArr.length != 3) {
            throw new IllegalFormatPrecisionException(timeArr.length);
        }
        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);
        int second = Integer.parseInt(timeArr[2]);
        if (hour < 0 || hour > 23) {
            throw new IllegalFormatPrecisionException(hour);
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalFormatPrecisionException(minute);
        }
        if (second < 0 || second > 59) {
            throw new IllegalFormatPrecisionException(second);
        }
        return LocalTime.of(hour, minute, second);
    }
}
