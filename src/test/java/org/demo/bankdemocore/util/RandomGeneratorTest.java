package org.demo.bankdemocore.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorTest {

    @Test
    public void randomDecimalScaleCheck() {
        BigDecimal actualNumber = RandomGenerator.generateRandomBigDecimalFromRange(BigDecimal.ONE, BigDecimal.valueOf(2));
        assertThat(actualNumber).isBetween(BigDecimal.ONE, BigDecimal.valueOf(2));
        assertThat(actualNumber.scale()).isEqualTo(4);
    }
}
