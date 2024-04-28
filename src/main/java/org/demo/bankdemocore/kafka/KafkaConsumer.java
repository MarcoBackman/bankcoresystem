package org.demo.bankdemocore.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final String groupId;

    KafkaConsumer(@Value("${spring.kafka.consumer.group-id}") String groupId) {
        this.groupId = groupId;
    }

    @KafkaListener(topics = "bankTopic", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group " + groupId + ": " + message);
    }
}
