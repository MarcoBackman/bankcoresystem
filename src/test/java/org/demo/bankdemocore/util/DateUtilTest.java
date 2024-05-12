package org.demo.bankdemocore.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {

    @Test
    public void testLocalDateTimeToLocalDate() {
        LocalDate localDate = LocalDate.of(2024, 5, 11);
        LocalTime localTime = LocalTime.of(6, 30);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        LocalDate actualLocalDate = DateUtil.toLocalDate(localDateTime);
        assertThat(actualLocalDate.getYear()).isEqualTo(2024);
        assertThat(actualLocalDate.getMonth()).isEqualTo(Month.of(5));
        assertThat(actualLocalDate.getDayOfMonth()).isEqualTo(11); 
    }
}
