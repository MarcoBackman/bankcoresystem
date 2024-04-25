package org.demo.bankdemo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RandomGenerator {
    public static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(4, RoundingMode.HALF_UP);
    }
}
