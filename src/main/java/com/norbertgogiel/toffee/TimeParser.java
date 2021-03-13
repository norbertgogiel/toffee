package com.norbertgogiel.toffee;

import java.time.DateTimeException;
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
           throwDateTimeException("hour (0 - 23)", hour);
        }
        if (minute < 0 || minute > 59) {
            throwDateTimeException("minute (0 - 59)", minute);
        }
        if (second < 0 || second > 59) {
            throwDateTimeException("second (0 - 59)", second);
        }
        return LocalTime.of(hour, minute, second);
    }

    private void throwDateTimeException(String message, int actualValue) {
        throw new DateTimeException("Invalid value for "
                .concat(message)
                .concat(". Actual value: ")
                .concat(String.valueOf(actualValue))
        );
    }
}
