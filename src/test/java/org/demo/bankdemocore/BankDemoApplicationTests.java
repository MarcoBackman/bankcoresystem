package org.demo.bankdemocore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles(value = "default,test")
@TestPropertySource("classpath:application-test.properties")
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
class BankDemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
