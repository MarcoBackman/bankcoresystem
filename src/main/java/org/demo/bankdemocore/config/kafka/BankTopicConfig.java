package org.demo.bankdemocore.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BankTopicConfig {

    @Value(value = "${spring.kafka.bank.partition.count}")
    private int numberOfPartition;

    @Value(value = "${spring.kafka.bank.partition.replication}")
    private int replicationCount;

    @Value(value = "${spring.kafka.bank.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic bankTopic() {
        return new NewTopic("bankTopic", numberOfPartition, (short) replicationCount);
    }
}
