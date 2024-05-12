package org.demo.bankdemocore.util;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {
    @Nullable
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDate.of(
                localDateTime.getYear(),
                localDateTime.getMonth(),
                localDateTime.getDayOfMonth()
        );
    }
}
