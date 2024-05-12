package org.demo.bankdemocore.config.db;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DSLConfig {
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public DSLContext getDSLContext(@Autowired DataSource dataSource,
                                    @Autowired PlatformTransactionManager provider) {
        var defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setTransactionProvider(new SpringTransactionProvider(provider));
        defaultConfiguration.setConnectionProvider(new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource)));
        defaultConfiguration.set(connectionProvider(dataSource));
        defaultConfiguration.set(SQLDialect.POSTGRES);
        return DSL.using(defaultConfiguration);
    }
}
