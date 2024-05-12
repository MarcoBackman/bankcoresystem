package org.demo.bankdemocore.repository;

import org.demo.bankdemocore.BankDemoApplication;
import org.demo.bankdemocore.config.db.DSLConfig;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@JooqTest
@Import({DSLConfig.class, TransactionAutoConfiguration.class, JooqAutoConfiguration.class})
@ContextConfiguration(classes = BankDemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Testcontainers
public @interface BlockingDatabaseTest {
}

